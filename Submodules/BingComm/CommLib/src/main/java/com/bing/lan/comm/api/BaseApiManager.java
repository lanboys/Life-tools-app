package com.bing.lan.comm.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.bing.lan.comm.api.interceptor.AddCookiesInterceptor;
import com.bing.lan.comm.api.interceptor.SaveCookiesInterceptor;
import com.bing.lan.comm.app.AppConfig;
import com.bing.lan.comm.app.AppUtil;
import com.bing.lan.comm.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 520 on 2017/7/11.
 */

public abstract class BaseApiManager {

    private static final int DEFAULT_CONN_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 600;
    private static final int READ_TIMEOUT = 600;
    private static final String COOKIE_PREF = "cookie_perfs";
    private static final LogUtil log1 = LogUtil.getLogUtil(BaseApiManager.class, LogUtil.LOG_VERBOSE);
    private static OkHttpClient sOkHttpClient;
    private static Gson sGson;
    protected final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    protected BaseApiManager() {

        if (sOkHttpClient == null) {
            sOkHttpClient = getClientBuilder().build();
        }
        if (sGson == null) {
            sGson = getGsonBuilder().create();
        }
    }

    public static OkHttpClient getOkHttpClient() {
        return sOkHttpClient;
    }

    public static void clearCookies() {

        log1.v("clearCookies(): 清除cookie成功");
        SharedPreferences sp = AppUtil.getAppContext().getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sp.edit();
        edit.clear().apply();
    }

    private static SSLSocketFactory getCertificates111() {
        SSLContext sslContext = null;
        try {
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput = new BufferedInputStream(new FileInputStream("load-der.crt"));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Create an SSLContext that uses our TrustManager
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sslContext != null ? sslContext.getSocketFactory() : null;
        // Tell the URLConnection to use a SocketFactory from our SSLContext
        //        URL url = new URL("https://certs.cac.washington.edu/CAtest/");
        //        HttpsURLConnection urlConnection =
        //                (HttpsURLConnection)url.openConnection();
        //        urlConnection.setSSLSocketFactory(context.getSocketFactory());
        //        InputStream in = urlConnection.getInputStream();
        //        copyInputStreamToOutputStream(in, System.out);
    }

    protected Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(sOkHttpClient)
                // .addConverterFactory(ScalarsConverterFactory.create()) //增加返回值为String的支持
                .addConverterFactory(GsonConverterFactory.create(sGson)) //增加返回值为Gson的支持(以实体类返回)
                //.addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //增加返回值为Observable<T>的支持
                .build();
    }

    private OkHttpClient.Builder getClientBuilder() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // logging
        if (AppConfig.LOG_DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        //https
        SSLSocketFactory certificates = getUnsafeCertificates();
        //SSLSocketFactory certificates = getCertificates();
        if (certificates != null) {
            builder.sslSocketFactory(certificates);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }

        // http cache
        Cache cache = new Cache(getHttpCacheFile(), 10 * 1024 * 1024);// 10MB
        builder.cache(cache);

        // cache control
        //CacheControlInterceptor interceptor = new CacheControlInterceptor();
        //builder.addNetworkInterceptor(interceptor);
        //builder.addInterceptor(interceptor);

        // 设置 Cookie
        builder.addInterceptor(new AddCookiesInterceptor(AppUtil.getAppContext()));
        builder.addInterceptor(new SaveCookiesInterceptor(AppUtil.getAppContext()));

        //方便子类中添加一些拦截器
        setInterceptor(builder);

        // createTime out
        builder.connectTimeout(DEFAULT_CONN_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);

        // retry mechanism 设置出现错误进行重新连接
        builder.retryOnConnectionFailure(true);

        return builder;
    }

    protected void setInterceptor(OkHttpClient.Builder builder) {

    }

    private GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
                .setDateFormat("yyyyMMdd")
                .disableInnerClassSerialization()
                .generateNonExecutableJson()
                .disableHtmlEscaping()
                .setLenient()//com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 4 path $[0].
                .setPrettyPrinting();
    }

    //private void initHttps(OkHttpClient.Builder builder) throws Exception {
    //
    //    //SSLSocketFactory sslSocketFactory = new SslContextFactory().getSslSocket().getSocketFactory();
    //    //builder.sslSocketFactory(sslSocketFactory);
    //
    //    //---------------------------------------------------------------------------------
    //
    //    if (BuildConfig.BUILD_TYPES != MyBuildType.BUILD_TYPE_DEBUG) {
    //        SSLSocketFactory certificates = getCertificates();
    //        if (certificates != null) {
    //            builder.sslSocketFactory(certificates);
    //        }
    //    }
    //    //---------------------------------------------------------------------------------
    //    //InputStream inputStream = AppUtil.openRawResource(R.raw.jzk12);  // 证书
    //    //InputStream inputStream = new Buffer().writeUtf8(CER_12306)                .inputStream();
    //    //HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{inputStream}, null, null);
    //    //builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
    //
    //    //---------------------------------------------------------------------------------
    //    //SSLContext sc = SSLContext.getInstance("SSL");
    //    //sc.init(null, new TrustManager[]{new X509TrustManager() {
    //    //    @Override
    //    //    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    //    //
    //    //    }
    //    //
    //    //    @Override
    //    //    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    //    //
    //    //    }
    //    //
    //    //    @Override
    //    //    public X509Certificate[] getAcceptedIssuers() {
    //    //        return null;
    //    //    }
    //    //}}, new SecureRandom());
    //    //
    //    //builder.sslSocketFactory(sc.getSocketFactory());
    //    //builder.hostnameVerifier(new HostnameVerifier() {
    //    //    @Override
    //    //    public boolean verify(String hostname, SSLSession session) {
    //    //        return true;
    //    //    }
    //    //});
    //
    //}

    private File getHttpCacheFile() {
        //File cacheFile = new File(AppUtil.getAppContext().getCacheDir() + File.pathSeparator + "httpCache");
        File cacheFile = AppUtil.getAppContext().getExternalFilesDir("http");
        log.i("getHttpCacheFile(): httpCacheFile: " + cacheFile);
        return cacheFile;
    }

    // 配置 ssl
    private SSLSocketFactory getCertificates() {
        InputStream inputStream = getCertificatesInputStream();
        if (inputStream == null) {
            return null;
        }

        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(inputStream);
            log.i("getCertificates(): " + cert.toString());

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("0", cert);

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            log.e("getCertificates():  加载证书异常： ", e);
        }

        return null;
    }

    private SSLSocketFactory getUnsafeCertificates() {
        //http://blog.csdn.net/voidmain_123/article/details/52703464

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            log.e("getCertificates():  加载不安全证书异常： ", e);
        }
        return null;
    }

    protected abstract InputStream getCertificatesInputStream();

    //public static SSLContext getSSLContext() {

    //SSLContext sslContext = null;
    //try {
    //    // 设定Security的Provider提供程序
    //    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    //
    //    // 建立空BKS,android只能用BKS(BouncyCastle密库)，一般java应用参数传JKS(java自带密库)
    //    KeyStore ksKeys = KeyStore.getInstance("BKS");
    //    ksKeys.load(null, null);
    //
    //    // 读入客户端证书
    //    PEMReader cacertfile = new PEMReader(new InputStreamReader(GameActivity.Instance.getAssets().open("cacert.pem")));
    //    X509Certificate cacert = (X509Certificate) cacertfile.readObject();
    //    cacertfile.close();
    //
    //    // 导入根证书作为trustedEntry
    //    KeyStore.TrustedCertificateEntry trustedEntry = new KeyStore.TrustedCertificateEntry(cacert);
    //    ksKeys.setEntry("ca_root", trustedEntry, null);
    //
    //    // 构建KeyManager、TrustManager
    //    /*KeyManagerFactory kmf = KeyManagerFactory
    //            .getInstance(KeyManagerFactory.getDefaultAlgorithm());
    //    kmf.init(ksKeys, null);*/
    //    TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");//密钥管理器,一般java应用传SunX509
    //    tmf.init(ksKeys);
    //
    //    // 构建SSLContext，此处传入参数为TLS，也可以为SSL
    //    sslContext = SSLContext.getInstance("TLS");
    //    sslContext.init(null, tmf.getTrustManagers(), null);
    //
    //} catch (Exception e) {
    //    e.printStackTrace();
    //}
    //
    //return sslContext;
    //}
}
