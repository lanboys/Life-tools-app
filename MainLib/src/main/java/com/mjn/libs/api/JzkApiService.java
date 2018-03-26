//package com.mjn.libs.api;
//
//import com.bing.lan.comm.api.HttpResult;
//import com.juzhongke.jzkseller.base.AppUpdateInfoBean;
//import com.juzhongke.jzkseller.ui.asset.addPublicCard.bean.BankCardDetailResultBean;
//import com.juzhongke.jzkseller.ui.asset.applyMoneyList.bean.ApplyCountsInfoBean;
//import com.juzhongke.jzkseller.ui.asset.applyMoneyList.bean.ApplyMoneyResultBean;
//import com.juzhongke.jzkseller.ui.asset.applyMoneyList.bean.CheckoutPayPswInfoBean;
//import com.juzhongke.jzkseller.ui.asset.applyMoneyList.bean.DefaultBankResultBean;
//import com.juzhongke.jzkseller.ui.asset.asset.bean.AssetResultBean;
//import com.juzhongke.jzkseller.ui.asset.asset.bean.ShopAuthStatusResultBean;
//import com.juzhongke.jzkseller.ui.asset.bountyList.bean.BountyResultBean;
//import com.juzhongke.jzkseller.ui.asset.incomeList.bean.IncomeResultBean;
//import com.juzhongke.jzkseller.ui.asset.manageCardList.bean.AddEditBankResultBean;
//import com.juzhongke.jzkseller.ui.asset.manageCardList.bean.DeleteBankResultBean;
//import com.juzhongke.jzkseller.ui.asset.manageCardList.bean.ManagerCardResultBean;
//import com.juzhongke.jzkseller.ui.asset.manageCardList.bean.SetBankResultBean;
//import com.juzhongke.jzkseller.ui.asset.notAvailableDetail.bean.NotAvailableDetailResultBean;
//import com.juzhongke.jzkseller.ui.asset.notAvailableList.bean.NotAvailableResultBean;
//import com.juzhongke.jzkseller.ui.asset.notSettlementDetail.bean.NotSettlementDetailResultBean;
//import com.juzhongke.jzkseller.ui.asset.notSettlementList.bean.NotSettleResultBean;
//import com.juzhongke.jzkseller.ui.asset.takeMoneyDetail.bean.TakeMoneyDetailResultBean;
//import com.juzhongke.jzkseller.ui.asset.takeMoneyList.bean.TakeMoneyResultBean;
//import com.juzhongke.jzkseller.ui.eat.dishSearch.bean.DishSearchResultBean;
//import com.juzhongke.jzkseller.ui.eat.eating.bean.EatingResultBean;
//import com.juzhongke.jzkseller.ui.eat.orderDishes.bean.bean.GoodsListResultBean;
//import com.juzhongke.jzkseller.ui.eat.orderManagement.bean.TableEatListResultBean;
//import com.juzhongke.jzkseller.ui.goods.addMenu.bean.GoodsDetailBean;
//import com.juzhongke.jzkseller.ui.goods.addMenu.bean.UpLoadImageBean;
//import com.juzhongke.jzkseller.ui.goods.allGoods.bean.AllGoodsResultBean;
//import com.juzhongke.jzkseller.ui.goods.dishesGood.bean.CategoryBean;
//import com.juzhongke.jzkseller.ui.goods.dishesGood.bean.CategoryGoodBean;
//import com.juzhongke.jzkseller.ui.goods.menu.bean.MenuResultBean;
//import com.juzhongke.jzkseller.ui.goods.menuDetail.bean.MenuDetailResultBean;
//import com.juzhongke.jzkseller.ui.goods.menuGroup.bean.MenuGroupResultBean;
//import com.juzhongke.jzkseller.ui.goods.menuGroupDetail.bean.GroupDetailBean;
//import com.juzhongke.jzkseller.ui.goods.search.bean.SearchResultBean;
//import com.juzhongke.jzkseller.ui.home.homeFragment.bean.DayAssetResultBean;
//import com.juzhongke.jzkseller.ui.home.homeFragment.bean.ShopConfigInfoBean;
//import com.juzhongke.jzkseller.ui.home.homeFragment.bean.ShopSoftwareTypeResult;
//import com.juzhongke.jzkseller.ui.home.homeFragment.bean.UserLevelInfoResultBean;
//import com.juzhongke.jzkseller.ui.home.shopPoster.bean.CreatePosterInfo;
//import com.juzhongke.jzkseller.ui.home.shopPoster.bean.PosterInfoBean;
//import com.juzhongke.jzkseller.ui.login.login.bean.LoginResultBean;
//import com.juzhongke.jzkseller.ui.login.register.bean.RegisterInfoBean;
//import com.juzhongke.jzkseller.ui.login.register.bean.RegisterResultBean;
//import com.juzhongke.jzkseller.ui.login.splash.bean.VisitorInfoBean;
//import com.juzhongke.jzkseller.ui.market.addQrcodeOrder.QrcodeAddBean;
//import com.juzhongke.jzkseller.ui.market.addQrcodeOrder.QrcodeUserResultBean;
//import com.juzhongke.jzkseller.ui.market.coupon.coupon.bean.CouponResultBean;
//import com.juzhongke.jzkseller.ui.market.fullDelivery.bean.FullResultBean;
//import com.juzhongke.jzkseller.ui.market.qrcodeOrder.bean.QrcodeOrderResultBean;
//import com.juzhongke.jzkseller.ui.order.order.order.bean.OrderResultBean;
//import com.juzhongke.jzkseller.ui.order.order.order.bean.PayResultBean;
//import com.juzhongke.jzkseller.ui.order.order.order.bean.ScanResultBean;
//import com.juzhongke.jzkseller.ui.order.orderDetail.bean.OrderDetailResultBean;
//import com.juzhongke.jzkseller.ui.pay.bean.GiftResultBean;
//import com.juzhongke.jzkseller.ui.printer.printerList.bean.PrinterListResultBean;
//import com.juzhongke.jzkseller.ui.setting.clerkList.bean.ClerkResultBean;
//import com.juzhongke.jzkseller.ui.setting.setting.bean.SettingShopDetailInfoBean;
//import com.juzhongke.jzkseller.ui.shop.claimShop.bean.ClaimShopResultBean;
//import com.juzhongke.jzkseller.ui.shop.createShop.bean.CreateShopBean;
//import com.juzhongke.jzkseller.ui.shop.shopAuth.bean.ShopAuthenticateResultBean;
//import com.juzhongke.jzkseller.ui.shop.shopAuth.bean.ShopDetailInfoBean;
//import com.juzhongke.jzkseller.ui.shop.shopList.bean.ShopListResultBean;
//import com.juzhongke.jzkseller.ui.shop.submitQualifications.bean.QualificationResultBean;
//import com.juzhongke.jzkseller.ui.software.softwareList.bean.SoftWareInfoBean;
//import com.juzhongke.jzkseller.ui.table.addTable.bean.TableDetailInfoBean;
//import com.juzhongke.jzkseller.ui.table.tableManagement.bean.TableListResultBean;
//import com.juzhongke.jzkseller.ui.table.tableType.bean.TableTypeInfoBean;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Map;
//
//import io.github.xiong_it.easypay.SoftPayResultBean;
//import io.reactivex.Observable;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import okhttp3.ResponseBody;
//import retrofit2.http.Field;
//import retrofit2.http.FieldMap;
//import retrofit2.http.FormUrlEncoded;
//import retrofit2.http.GET;
//import retrofit2.http.Header;
//import retrofit2.http.Multipart;
//import retrofit2.http.POST;
//import retrofit2.http.Part;
//import retrofit2.http.Query;
//import retrofit2.http.Streaming;
//import retrofit2.http.Url;
//
///**
// * Created by win7 on 2017/4/21.
// */
//public interface JzkApiService {
//
//    // http://blog.csdn.net/qq_21430549/article/details/51227379
//    //批量上传
//    //默认不加水印
//    @Multipart
//    @POST("public/upload?parms=true&mark=0")
//    Observable<UpLoadImageBean> uploadImageFile(
//            @Part MultipartBody.Part file);
//
//    //不加水印
//    @Multipart
//    @POST("public/upload?parms=true")
//    Observable<HttpResult<UploadImageFile>> uploadImageFile(
//            @Part MultipartBody.Part file, @Query("mark") String mark);
//
//    @Multipart
//    @POST("public/upload?parms=true")
//    Observable<HttpResult<UploadImageFile>> uploadImageFile(
//            @Part("Upload[file]\"; filename=\"avatar.jpg") RequestBody body
//    );
//
//    //可以监控进度
//    @Multipart
//    @POST("public/upload?parms=true")
//    Observable<HttpResult<UploadImageFile>> uploadImageFile(
//            @Part MultipartBody.Part file,
//            @Header("ProgressId") int progressId);
//
//    //可以监控进度
//    //avatar名字不可以自己定义
//    @Multipart
//    @POST("public/upload?parms=true")
//    Observable<HttpResult<UploadImageFile>> uploadImageFile(
//            @Part("Upload[file]\"; filename=\"avatar.jpg") RequestBody body,
//            @Header("ProgressId") int progressId
//    );
//
//    /**
//     * 获取角色
//     */
//    @GET("goods/shop/user-role")
//    Observable<UserLevelInfoResultBean> getShopUserRole(
//            @Query("userId") String userId,
//            @Query("shopId") String shopId
//    );
//
//    /**
//     * 获取店铺营业时间
//     */
//    @GET("manage/shop/time")
//    Observable<HttpResult<String>> getShopTimeStatus(@Query("shopId") String shopId);
//
//    /**
//     * 店铺营业时间
//     * 正常营业 1   打烊(休息) 0  未营业 -3
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/set-shopstatus")
//    Observable<HttpResult<Object>> setShopTimeStatus(
//            @Field("shopId") String shopId,
//            @Field("status") String status
//    );
//
//    /**
//     * 获取店铺系统信息和订购信息
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/get-shoptype")
//    Observable<HttpResult<ShopSoftwareTypeResult>> getShopSoftType(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId
//    );
//
//    /**
//     * /**
//     * 添加海报
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/poster")
//    Observable<HttpResult<CreatePosterInfo>> createPoster(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("title") String title,
//            @Field("pic") String pic,
//            @Field("goodList") String goodList,
//            @Field("desc") String desc,
//            @Field("goods") String goods
//    );
//
//    /**
//     * 获取海报
//     */
//    @GET("manage/shop/poster")
//    Observable<HttpResult<PosterInfoBean>> getPoster(@Query("shopId") String shopId);
//
//    /**
//     * 获取打印机状态
//     */
//    @FormUrlEncoded
//    @POST("seting/device/del-device")
//    Observable<HttpResult<Object>> getPrinterConnectStatus(@Field("shopId") String shopId);
//
//    /**
//     * 获取店铺配置状态
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/check-config")
//    Observable<HttpResult<ShopConfigInfoBean>> getShopConfig(@Field("shopId") String shopId);
//
//    /**
//     * 创建店铺
//     */
//    // @Multipart
//    @FormUrlEncoded
//    @POST("manage/shop/add")
//    Observable<HttpResult<CreateShopBean>> createShop(@FieldMap Map<String, String> map);
//
//    /**
//     * 编辑店铺信息
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/edit")
//    Observable<HttpResult<CreateShopBean>> editShop(@FieldMap Map<String, String> map);
//
//    /**
//     * 认领店铺
//     */
//    @FormUrlEncoded
//    @POST("platform/shop/claim")
//    Observable<HttpResult<ClaimShopResultBean>> claimShop(@FieldMap Map<String, String> map);
//
//    /**
//     * 接口名称 提交资质证明
//     * 请求类型 post
//     * 请求Url  platform/shop/proof
//     */
//    @FormUrlEncoded
//    @POST("platform/shop/proof")
//    Observable<HttpResult> proveShop(@FieldMap Map<String, String> map);
//
//    /**
//     * 重新认领门店
//     */
//    @FormUrlEncoded
//    @POST("platform/shop/reclaim")
//    Observable<HttpResult<Object>> claimShopAgain(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId
//    );
//
//    /**
//     * 提交门店认证资料
//     */
//    @Multipart
//    @POST("goods/shop/cert-save")
//    //Observable<HttpResult<ShopAuthenticateResultBean>> uploadShopAuthenticate(@PartMap Map<String, RequestBody> map);
//    Observable<HttpResult<ShopAuthenticateResultBean>> uploadShopAuthenticate(
//            @Part("shop_id") RequestBody shop_id,
//            @Part("store_id") RequestBody store_id,
//            @Part("companyName") RequestBody companyName,
//            @Part("idCardName") RequestBody idCardName,
//            @Part("idCardNO") RequestBody idCardNO,
//            @Part("businessLicense") RequestBody businessLicense,
//            @Part("type") RequestBody type,
//            @Part("Upload[file][]\"; filename=\"avatar0.jpg") RequestBody body0,
//            @Part("Upload[file][]\"; filename=\"avatar1.jpg") RequestBody body1,
//            @Part("Upload[file][]\"; filename=\"avatar2.jpg") RequestBody body2,
//            @Part("Upload[file][]\"; filename=\"avatar3.jpg") RequestBody body3,
//            @Part("userId") RequestBody userId
//    );
//
//    /**
//     * 提交门店认证资料
//     */
//    @FormUrlEncoded
//    @POST("goods/shop/cert-save")
//    Observable<HttpResult<ShopAuthenticateResultBean>> shopAuth(@FieldMap Map<String, String> map);
//    //Observable<HttpResult<ShopAuthenticateResultBean>> uploadShopAuthenticate(
//    //        @Part("shop_id") RequestBody shop_id,
//    //        @Part("store_id") RequestBody store_id,
//    //        @Part("companyName") RequestBody companyName,
//    //        @Part("idCardName") RequestBody idCardName,
//    //        @Part("idCardNO") RequestBody idCardNO,
//    //        @Part("businessLicense") RequestBody businessLicense,
//    //        @Part("type") RequestBody type,
//    //        @Part("Upload[file][]\"; filename=\"avatar0.jpg") RequestBody body0,
//    //        @Part("Upload[file][]\"; filename=\"avatar1.jpg") RequestBody body1,
//    //        @Part("Upload[file][]\"; filename=\"avatar2.jpg") RequestBody body2,
//    //        @Part("userId") RequestBody userId
//    //);
//
//    /**
//     * 门店认证资料详情
//     */
//    //@FormUrlEncoded
//    @GET("goods/shop/cert")
//    Observable<HttpResult<ShopDetailInfoBean>> detailShopAuth(@Query("shopId") String shopId);
//
//    /**
//     * 设置页面店铺详情
//     */
//    //@FormUrlEncoded
//    @GET("manage/shop/info")
//    Observable<HttpResult<SettingShopDetailInfoBean>> settingShopDetail(
//            @Query("userId") String userId,
//            @Query("shopId") String shopId
//    );
//
//    // @GET("strore/store-list")
//    // Observable<HttpResult<ShopResultBean>> loadShopList(@QueryMap Map<String, String> map);
//
//    /**
//     * 登录
//     */
//    @FormUrlEncoded
//    @POST("platform/login/login")
//    Observable<HttpResult<LoginResultBean>> login(@FieldMap Map<String, String> map);
//
//    /**
//     * 获取登录时 的图片验证码
//     */
//    @GET("platform/login/captcha")
//    Observable<HttpResult<String>> getLoginVcode(@Query("type") String type);
//
//    /**
//     * 更新推送 app设备信息
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/phone-token")
//    Observable<HttpResult<Object>> updatePushInfo(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("type") String type,
//            @Field("phone") String phone,
//            @Field("alias") String alias,
//            @Field("token") String token
//    );
//
//    /**
//     * 登出
//     */
//    //@FormUrlEncoded
//    //@POST("login-ios/logout")
//    //Observable<HttpResult<Object>> logout(@Field("userId") String userId);
//    @POST("login-ios/logout")
//    Observable<HttpResult<Object>> logout();
//
//    /**
//     * 找回密码1
//     *
//     * @param phone
//     * @param ctype
//     * @param code
//     * @return
//     */
//    @GET("login-ios/found-pass")
//    Observable<RegisterResultBean> foundPassword1(
//            @Query("phone") String phone,
//            @Query("ctype") String ctype,
//            @Query("code") String code
//    );
//
//    /**
//     * 检测是否注册
//     */
//    @FormUrlEncoded
//    @POST("platform/login/check-user")
//    Observable<HttpResult<Object>> checkUser(@Field("phone") String phone);
//
//    /**
//     * 获取验证码
//     *
//     * @param type 1,注册,2登录,3找回密码,4删除店铺,5支付密码,6认证,18添加银行卡，19修改银行卡
//     */
//    @FormUrlEncoded
//    @POST("platform/login/send-code")
//    Observable<HttpResult<Object>> getVcode(
//            @Field("phone") String phone,
//            @Field("type") String type
//    );
//
//    /**
//     * 找回密码时  检测验证码是否正确
//     */
//    @FormUrlEncoded
//    @POST("platform/login/check-code")
//    Observable<HttpResult<Object>> checkVcode(
//            @Field("phone") String phone,
//            @Field("type") String type,
//            @Field("code") String code
//
//    );
//
//    /**
//     * 注册
//     */
//    @FormUrlEncoded
//    @POST("platform/login/reg")
//    Observable<HttpResult<RegisterInfoBean>> register(
//            @Field("code") String code,
//            //@Field("username") String username,
//            @Field("phone") String phone,
//            @Field("password") String password,
//            @Field("reg_client") String reg_client
//    );
//
//    /**
//     * 找回密码
//     */
//    @FormUrlEncoded
//    @POST("platform/login/found-pass")
//    Observable<HttpResult<Object>> foundPassword(
//            @Field("phone") String phone,
//            @Field("newPwd") String newPwd,
//            @Field("newPwd_ag") String newPwd_ag
//    );
//
//    /**
//     * 我的资产
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/my-assets")
//    Observable<HttpResult<AssetResultBean>> myAsset(@Field("shopId") String shopId);
//
//    /**
//     * 判断店铺认证状态
//     */
//    @FormUrlEncoded
//    @POST("public/judge-cert")
//    Observable<HttpResult<ShopAuthStatusResultBean>> shopAuthStatus(@Field("shopId") String shopId);
//
//    /**
//     * 收支明细
//     */
//    @GET("assets/assets/income-pay")
//    Observable<HttpResult<IncomeResultBean>> incomeDetail(
//            @Query("shopId") String shopId,
//            @Query("accountId") String accountId,
//            @Query("pageNum") String pageNum
//    );
//
//    /**
//     * 奖励金额列表
//     */
//    @GET("assets/assets/activi-moneylist")
//    Observable<HttpResult<BountyResultBean>> bountyList(
//            @Query("accountId") String accountId,
//            @Query("pageNum") String pageNum
//    );
//
//    /**
//     * 显示默认的银行
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/bank-default")
//    Observable<HttpResult<DefaultBankResultBean>> defaultBank(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId
//    );
//
//    /**
//     * 申请提现
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/apply")
//    Observable<HttpResult<ApplyMoneyResultBean>> applyMoney(
//            @Field("userId") String userId,
//            @Field("bankId") String bankId,
//            @Field("applyAmount") String applyAmount,
//            @Field("normal") String normal,
//            @Field("createUser") String createUser,
//            @Field("shopId") String shopId,
//            @Field("source") String source,
//            @Field("payType") String payType
//    );
//
//    /**
//     * 提现记录
//     */
//    @GET("assets/assets/apply-list")
//    Observable<HttpResult<TakeMoneyResultBean>> takeMoney(
//            @Query("shopId") String shopId,
//            @Query("pageNum") String pageNum
//    );
//
//    /**
//     * 提现记录详情
//     */
//    @GET("assets/assets/apply-detail")
//    Observable<HttpResult<TakeMoneyDetailResultBean>> applyMoneyDetail(
//            @Query("withId") String withId
//    );
//
//    /**
//     * 今日收益
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/day-assets")
//    Observable<HttpResult<DayAssetResultBean>> dayAssets(
//            @Field("shopId") String shopId
//    );
//
//    /**
//     * 不可用金额
//     */
//    @GET("assets/assets/freeze")
//    Observable<HttpResult<NotAvailableResultBean>> notAvailable(
//            @Query("shopId") String shopId,
//            @Query("pageNum") String pageNum
//    );
//
//    /**
//     * 不可用金额详情
//     */
//    @GET("assets/assets/freeze-detail")
//    Observable<HttpResult<NotAvailableDetailResultBean>> notAvailableDetail(
//            @Query("withId") String withId
//    );
//
//    /**
//     * 待结算金额
//     */
//    @GET("assets/assets/settled")
//    Observable<HttpResult<NotSettleResultBean>> notSettle(
//            @Query("shopId") String shopId,
//            @Query("pageNum") String pageNum
//
//    );
//
//    /**
//     * 待结算金额详情
//     */
//    @GET("assets/assets/settled-detail")
//    Observable<HttpResult<NotSettlementDetailResultBean>> notSettlementDetail(
//            @Query("orderId") String orderId
//    );
//
//    /**
//     * 银行卡列表
//     */
//    @GET("assets/assets/bank-list")
//    Observable<HttpResult<ManagerCardResultBean>> bankCardList(
//            @Query("userId") String userId,
//            @Query("shopId") String shopId,
//            @Query("pageNum") String pageNum
//    );
//
//    /**
//     * 设置默认银行卡
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/set-bank")
//    Observable<HttpResult<SetBankResultBean>> setDefaultBankCard(
//            @Field("userId") String userId,
//            @Field("bankId") String bankId,
//            @Field("shopId") String shopId
//    );
//
//    /**
//     * 银行卡修改
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/bank-edit")
//    Observable<HttpResult<AddEditBankResultBean>> bankCardEdit(@FieldMap Map<String, String> map);
//
//    /**
//     * 银行卡添加
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/bank-add")
//    Observable<HttpResult<AddEditBankResultBean>> bankCardAdd(@FieldMap Map<String, String> map);
//
//    /**
//     * 删除银行卡
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/bank-delete")
//    Observable<HttpResult<DeleteBankResultBean>> bankCardDelete(
//            @Field("bankId") String bankId
//    );
//
//    /**
//     * 银行卡验证码获取
//     *
//     * @param userId
//     * @return
//     */
//    //@FormUrlEncoded
//    //@POST("assets/assets/bank-code")
//    //Observable<HttpResult<String>> bankCardCode(@Field("userId") String userId);
//
//    /**
//     * 银行卡详情
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/bank-detail")
//    Observable<HttpResult<BankCardDetailResultBean>> bankCardDetail(
//            @Field("bankId") String bankId
//    );
//
//    /**
//     * 判断是否存在支付密码
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/judge-paypwd")
//    Observable<HttpResult<String>> judgePayPassword(@Field("userId") String userId);
//
//    /**
//     * 余额,提现和输错密码的次数
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/input-counts")
//    Observable<HttpResult<ApplyCountsInfoBean>> judgeCounts(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("type") String type
//    );
//
//    /**
//     * 检查余额
//     *
//     * @return 501;502;500；200
//     * <p>
//     * 申请金额大于余额;申请金额不能大于50万;没有余额；符合要求可提现
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/check-balance")
//    Observable<HttpResult<String>> checkBalance(
//            @Field("shopId") String shopId,
//            @Field("applyAmount") String applyAmount
//    );
//
//    /**
//     * 设置支付密码
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/set-paypwd")
//    Observable<HttpResult<String>> setPayPassword(
//            @Field("userId") String userId,
//            @Field("payPwd") String payPwd
//    );
//
//    /**
//     * 验证支付密码
//     */
//    @FormUrlEncoded
//    @POST("assets/assets/check-paypwd")
//    Observable<HttpResult<CheckoutPayPswInfoBean>> checkPayPassword(
//            @Field("userId") String userId,
//            @Field("payPwd") String payPwd
//    );
//
//    /**
//     * 店铺列表
//     */
//    @GET("manage/shop/list")
//    Observable<HttpResult<ShopListResultBean>> shopList(
//            @Query("userId") String userId,
//            @Query("pageNum") String pageNum,
//            @Query("pageSize") String pageSize
//    );
//
//    /**
//     * 购买服务套餐列表
//     */
//    @GET("manage/shop/server-list")
//    Observable<HttpResult<ArrayList<SoftWareInfoBean>>> softWareList(@Query("shop_id") String shopId);
//
//    /**
//     * 选择购买服务类型
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/choice-service")
//    Observable<HttpResult<SoftPayResultBean>> softChoiceService(@FieldMap Map<String, String> map);
//
//    ///**
//    // * 选择支付方式
//    // */
//    //@FormUrlEncoded
//    //@POST("manage/shop/pay-service")
//    //Observable<HttpResult<SoftPayResultBean>> softOrderService(@FieldMap Map<String, String> map);
//
//    /**
//     * 取消订单
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/service-cancel")
//    Observable<HttpResult<Object>> softOrderServiceCancel(@Field("shop_id") String shopId);
//
//    /**
//     * 订单详细内容
//     */
//    @GET("manage/shop/service-detail")
//    Observable<HttpResult<SoftPayResultBean>> softOrderServiceDetail(@Query("order_id") String orderId);
//
//    /**
//     * 不可用金额
//     */
//    @GET("seting/admin/cashier-list")
//    Observable<HttpResult<ClerkResultBean>> clerkList(
//            @Query("admin_id") String userId,
//            @Query("shopId") String shopId,
//            @Query("pageNum") String pageNum,
//            @Query("size") String size
//    );
//
//    @FormUrlEncoded
//    @POST("seting/admin/add-cashier")
//    Observable<HttpResult<Object>> addClerk(
//            @Field("admin_id") String userId,
//            @Field("shopId") String shopId,
//            @Field("level") String level,
//            @Field("real_name") String real_name,
//            @Field("phone") String phone,
//            @Field("telphone") String telphone,
//            @Field("nickname") String nickname
//    );
//
//    @FormUrlEncoded
//    @POST("seting/admin/cashier-save")
//    Observable<HttpResult<Object>> editClerk(
//            @Field("admin_id") String userId,
//            @Field("shopId") String shopId,
//            @Field("level") String level,
//            @Field("real_name") String real_name,
//            @Field("phone") String phone,
//            @Field("telphone") String telphone,
//            @Field("user_id") String user_id,
//            @Field("nickname") String nickname
//    );
//
//    @FormUrlEncoded
//    @POST("goods/shop/telephone-edit")
//    Observable<HttpResult<Object>> setServicePhone(
//            @Field("shopId") String shopId,
//            @Field("telephone") String telephone
//    );
//
//    //@Multipart
//    //@POST("login-ios/savefile")
//    //Observable<HttpResult<String>> errorReport(
//    //        @Part("Upload[file]\"; filename=\"log.txt") RequestBody body
//    //);
//
//    @Multipart
//    @POST("login-ios/savefile")
//    Observable<HttpResult<String>> errorReport(@Part MultipartBody.Part file);
//
//    @GET
//    Observable<HttpResult<ArrayList<AppUpdateInfoBean>>> checkAppUpdate(@Url String url);
//
//    @GET
//    Observable<AppUpdateInfoBean> checkAppUpdate1(@Url String url);
//
//    @FormUrlEncoded
//    @POST("public/check-version")
//    Observable<HttpResult<AppUpdateInfoBean>> checkAppUpdate(
//            @Field("client") String client,
//            @Field("name") String name,
//            @Field("versionNum") String versionNum);
//
//    @FormUrlEncoded
//    @POST("public/set-tourist")
//    Observable<HttpResult<VisitorInfoBean>> getVisitorInfo(@Field("time") String time);//post必须传参数
//
//    @GET
//    @Streaming
//    Observable<retrofit2.Response<ResponseBody>> downloadApp(
//            @Url String url,
//            @Header("ProgressId") int progressId);
//
//    @GET
//    @Streaming
//    Observable<File> downloadAppFile(@Url String url, @Header("ProgressId") int progressId);
//
//    /**
//     * @param type 1-申请桌帖操作，2-下载贴码操作，3-堂食点餐入口，4-扫码买单入口，5-店铺装潢;需要检查权限的操作
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/check-callpower")
//    Observable<HttpResult<Object>> checkClickPower(
//            @Field("shopId") String shopId,
//            @Field("type") String type);
//
//    /**
//     * 删除游客体验账号
//     */
//    @FormUrlEncoded
//    @POST("public/remove-tourist")
//    Observable<HttpResult<Object>> removeVisitor(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId);
//
//    /**
//     * 编辑店铺信息
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/edit-shopinfo")
//    Observable<HttpResult<Object>> editShopInfo(@FieldMap Map<String, String> map);
//
//    //====================桌位管理============================
//
//    /**
//     * 添加桌位类型
//     */
//    @FormUrlEncoded
//    @POST("table/tableclass/add")
//    Observable<HttpResult<Object>> addTableType(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("name") String name,
//            @Field("max") String max,
//            @Field("min") String min
//    );
//
//    /**
//     * 编辑桌位类型
//     */
//    @FormUrlEncoded
//    @POST("table/tableclass/edit")
//    Observable<HttpResult<Object>> editTableType(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("name") String name,
//            @Field("max") String max,
//            @Field("min") String min,
//            @Field("typeId") String typeId
//    );
//
//    /**
//     * 删除桌位类型
//     */
//    @FormUrlEncoded
//    @POST("table/tableclass/remove")
//    Observable<HttpResult<Object>> deleteTableType(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("typeId") String typeId
//    );
//
//    /**
//     * 桌位类型列表
//     */
//    @GET("table/tableclass/list")
//    Observable<HttpResult<ArrayList<TableTypeInfoBean>>> getTableTypeList(@Query("shopId") String shopId);
//
//    ///**
//    // * 桌位类型信息
//    // */
//    //
//    //@FormUrlEncoded
//    //@POST("table/tableclass/detail")
//    //Observable<HttpResult<TableTypeInfoBean>> detailTableType(
//    //        @Field("userId") String userId,
//    //        @Field("shopId") String shopId,
//    //        @Field("typeId") String typeId
//    //);
//
//    //====================桌位============================
//
//    /**
//     * 添加桌位
//     */
//    @FormUrlEncoded
//    @POST("goods/dinner/add")
//    Observable<HttpResult<Object>> addTable(
//            @Field("shopId") String shopId,
//            @Field("shopName") String shopName,
//            @Field("dinnerNo") String dinnerNo,
//            @Field("typeId") String typeId,
//            @Field("typeName") String typeName
//    );
//
//    /**
//     * 编辑桌位
//     */
//    @FormUrlEncoded
//    @POST("goods/dinner/edit")
//    Observable<HttpResult<Object>> editTable(
//            @Field("shopId") String shopId,
//            @Field("shopName") String shopName,
//            @Field("dinnerNo") String dinnerNo,
//            @Field("dinnerId") String dinnerId,
//            @Field("tableType") String tableType,
//            @Field("typeName") String typeName,
//            @Field("userId") String userId
//    );
//
//    /**
//     * 桌位 信息
//     */
//    @GET("goods/dinner/edit-index")
//    Observable<HttpResult<TableDetailInfoBean>> getTableDetail(
//            @Query("shopId") String shopId,
//            @Query("dinnerId") String dinnerId
//    );
//
//    /**
//     * 删除桌位
//     */
//    @GET("goods/dinner/delete")
//    Observable<HttpResult<Object>> deleteTable(
//            @Query("shopId") String shopId,
//            @Query("dinnerId") String dinnerId
//    );
//
//    /**
//     * 检测物料是否允许申请  1支付卡 2 桌位卡 3 点餐码
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/check-materials-repeat")
//    Observable<HttpResult<Object>> checkApplyQrcode(
//            @Field("shopId") String shopId,
//            @Field("type") String type
//    );
//
//    /**
//     * 餐桌二维码绑定
//     */
//    @FormUrlEncoded
//    @POST("goods/dinner/bind-tables")
//    Observable<HttpResult<String>> bindTable(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("tableType") String tableType,
//            @Field("tableTypeName") String tableTypeName,
//            @Field("dinnerNo") String dinnerNo,
//            @Field("num") String num,
//            @Field("token") String token
//    );
//
//    /**
//     * 申请物料
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/materials-apply")
//    Observable<HttpResult<Object>> applyQrcode(@FieldMap Map<String, String> map);
//
//    /**
//     * 桌位 列表
//     */
//    @FormUrlEncoded
//    @POST("goods/dinner/list")
//    Observable<HttpResult<ArrayList<TableListResultBean>>> getTableList(@Field("shopId") String shopId);
//
//    //====================点餐============================
//
//    /**
//     * 点餐页面桌位 列表
//     */
//    @GET("manage/meal/tables")
//    Observable<HttpResult<ArrayList<TableEatListResultBean>>> getEatTableList(@Query("shopId") String shopId);
//
//    /**
//     * 检查餐桌状态  餐桌状态，0-空闲1-点餐中 2-就餐中
//     */
//    @GET("manage/meal/status")
//    Observable<HttpResult<String>> checkTableStatus(
//            @Query("shopId") String shopId,
//            @Query("tableId") String tableId
//    );
//
//    /**
//     * 创建餐单
//     */
//    @FormUrlEncoded
//    @POST("manage/meal/add")
//    Observable<HttpResult<String>> createMenu(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("tablesId") String tablesId,
//            @Field("guestNum") String guestNum
//    );
//
//    /**
//     * 获取餐单Id
//     */
//    @GET("manage/meal/candan")
//    Observable<HttpResult<String>> getMenuId(
//            @Query("shopId") String shopId,
//            @Query("tableId") String tableId
//    );
//
//    /**
//     * 获取点餐菜品列表
//     */
//    @GET("manage/meal/goodslist")
//    Observable<HttpResult<GoodsListResultBean>> getDishList(
//            @Query("shopId") String shopId,
//            @Query("tablesId") String tablesId
//    );
//
//    /**
//     * 检查菜品库存
//     */
//    @GET("manage/meal/checksku")
//    Observable<HttpResult<String>> getDishList(
//            @Query("shopId") String shopId,
//            @Query("goodsId") String goodsId,
//            @Query("skuId") String skuId,
//            @Query("stockNum") String stockNum
//    );
//
//    /**
//     * 菜品搜索
//     */
//    @GET("manage/meal/search")
//    Observable<HttpResult<ArrayList<DishSearchResultBean>>> dishSearch(
//            @Query("shopId") String shopId,
//            @Query("title") String title
//    );
//
//    /**
//     * 更新餐单备注
//     */
//    @FormUrlEncoded
//    @POST("manage/meal/editremark")
//    Observable<HttpResult<Object>> updateNote(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("candanId") String candanId,
//            @Field("remark") String remark
//    );
//
//    /**
//     * 提交厨房或加菜
//     */
//    @FormUrlEncoded
//    @POST("manage/meal/edit")
//    Observable<HttpResult<Object>> orderDishOk(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("candanId") String candanId,
//            @Field("guestNum") String guestNum,
//            @Field("cyNum") String cyNum,
//            @Field("details") String details,
//            @Field("remark") String remark
//    );
//
//    /**
//     * 餐单详情
//     */
//    @GET("manage/meal/detail")
//    Observable<HttpResult<EatingResultBean>> menuOrderDetail(
//            @Query("shopId") String shopId,
//            @Query("candanId") String candanId
//    );
//
//    /**
//     * 退菜
//     */
//    @FormUrlEncoded
//    @POST("manage/meal/return")
//    Observable<HttpResult<Object>> dishReturn(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("candanId") String candanId,
//            @Field("detailId") String detailId,
//            @Field("returnNum") String returnNum
//    );
//
//    /**
//     * 退菜
//     */
//    @FormUrlEncoded
//    @POST("manage/meal/allreturn")
//    Observable<HttpResult<Object>> dishAllReturn(
//            @Field("userId") String userId,
//            @Field("shopId") String shopId,
//            @Field("candanId") String candanId,
//            @Field("details") String details
//    );
//
//    /**
//     * 取消餐单
//     */
//    @GET("manage/meal/cancel")
//    Observable<HttpResult<Object>> dishCancel(
//            @Query("userId") String userId,
//            @Query("shopId") String shopId,
//            @Query("candanId") String candanId
//    );
//
//    /**
//     * 检查餐单是否已生成订单  true表示未生成，false表示已生成，异常情况
//     */
//    @GET("manage/meal/checkorder")
//    Observable<HttpResult<Boolean>> checkDishOrder(
//            @Query("shopId") String shopId,
//            @Query("candanId") String candanId
//    );
//
//    /**
//     * 结账
//     */
//    @GET("manage/meal/qrcode")
//    Observable<HttpResult<String>> payBillQrcode(
//            @Query("candanNo") String candanNo
//    );
//
//    /**
//     * 标记收款
//     */
//    @GET("manage/meal/markcollect")
//    Observable<HttpResult<Object>> payBillMarkcollect(
//            @Query("userId") String userId,
//            @Query("shopId") String shopId,
//            @Query("candanId") String candanId
//    );
//
//    //====================打印机============================
//
//    /**
//     * 添加打印机
//     */
//    @FormUrlEncoded
//    @POST("seting/device/add-device")
//    Observable<HttpResult<Object>> addPrinter(@FieldMap Map<String, String> map);
//
//    /**
//     * 设置打印机
//     *
//     * @param
//     */
//    @FormUrlEncoded
//    @POST("seting/device/save-seting")
//    Observable<HttpResult<Object>> saveSettingPrinter(@FieldMap Map<String, String> map);
//
//    /**
//     * 连接打印机
//     *
//     * @param type 传2表示设为断开，传1表示设为链接
//     */
//    @FormUrlEncoded
//    @POST("seting/device/link-device")
//    Observable<HttpResult<Object>> connectPrinter(
//            @Field("deviceId") String deviceId,
//            @Field("imei") String imei,
//            @Field("devicePwd") String devicePwd,
//            @Field("type") String type,
//            @Field("shopId") String shopId,
//            @Field("printerType") String printerType
//    );
//
//    /**
//     * 删除打印机
//     */
//    @FormUrlEncoded
//    @POST("seting/device/del-device")
//    Observable<HttpResult<Object>> deletePrinter(
//            @Field("deviceId") String deviceId,
//            @Field("printerType") String printerType
//    );
//
//    /**
//     * 打印机列表
//     */
//    @GET("seting/device/list")
//    Observable<HttpResult<PrinterListResultBean>> printerList(
//            @Query("shopId") String shopId,
//            @Query("pageNum") String pageNum
//    );
//
//    /**
//     * 接口名称 调用打印机打印内容
//     */
//    @GET("seting/device/call-device")
//    Observable<HttpResult> addPrinterText(@Query("candanId") String candanId, @Query("status") String status,
//            @Query("callType") String callType, @Query("shopId") String shopId);
//
//    //====================点餐============================
//
//    /**           ----------------------------------------------------------------------------*/
//    /**
//     * 餐单列表
//     */
//    @GET("candan/candan/candan")
//    Observable<HttpResult<OrderResultBean>> getOrderList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize,
//            @Query("type") String type, @Query("shopId") String shopId,
//            @Query("orderType") String orderType, @Query("keywords") String keywords);
//
//    /**
//     * /**
//     * 扫码订单
//     */
//    @GET("candan/candan/candan")
//    Observable<HttpResult<ScanResultBean>> getScanList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize,
//            @Query("type") String type, @Query("shopId") String shopId,
//            @Query("orderType") String orderType, @Query("keywords") String keywords);
//
//    /**
//     * 接口名称 取消扫码买单订单
//     * 请求类型 post
//     * 请求Url  manage/shop/cancel-order
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/cancel-order")
//    Observable<HttpResult<String>> cancelScan(@Field("orderId") String orderId, @Field("shopId") String shopId,
//            @Field("userId") String userId);
//
//    /**
//     * 接口名称 扫码买单支付二维码
//     * 请求类型 get
//     * 请求Url  manage/shop/qrcode-order
//     */
//    @GET("manage/shop/qrcode-order")
//    Observable<HttpResult<String>> payScan(@Query("orderId") String orderId, @Query("shopId") String shopId);
//
//    /**
//     * 接口名称 扫码买单标记收款
//     * 请求类型 post
//     * 请求Url  manage/shop/mark-collect
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/mark-collect")
//    Observable<HttpResult<Object>> scanMark(@Field("orderNo") String orderNo, @Field("shopId") String shopId,
//            @Field("userId") String userId);
//
//    /**
//     * 餐单详情
//     */
//    @GET("candan/candan/detail")
//    Observable<OrderDetailResultBean> getOrderDetail(@Query("candanId") String candanId, @Query("shopId") String shopId);
//
//    /**
//     * 优惠券列表   接通
//     */
//    @GET("manage/shop/couponlist")
//    Observable<CouponResultBean> getCouponList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize,
//            @Query("status") int status, @Query("shopId") String shopId);
//
//    /**
//     * 支付有礼优惠券列表   接通
//     */
//    @GET("manage/shop/couponlist")
//    Observable<CouponResultBean> getPayCouponList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize,
//            @Query("status") int status, @Query("shopId") String shopId, @Query("type") String type);
//
//    /**
//     * 优惠券失效
//     */
//    @GET("manage/shop/couponlose")
//    Observable<HttpResult> couponclose(@Query("id") int id, @Query("shopId") String shopId, @Query("userId") String userId);
//
//    /**
//     * 优惠券新建  接通
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/coupon")
//    Observable<HttpResult> addCoupon(@Field("title") String title, @Field("price") String price,
//            @Field("totalCount") String totalCount, @Field("limitUse") String limitUse,
//            @Field("startTime") String startTime, @Field("endTime") String endTime,
//            @Field("isCostPrice") String isCostPrice, @Field("isOrderLimit") String isOrderLimit,
//            @Field("limitPrice") String limitPrice, @Field("shopId") String shopId,
//            @Field("userId") String userId, @Field("type") String type, @Field("isGift") String isGift);
//
//    /**
//     * 接口名称 优惠券删除
//     * 请求类型 post
//     * 请求Url  manage/shop/coupondelete
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/coupondelete")
//    Observable<HttpResult> deleteCoupon(@Field("id") String id, @Field("shopId") String shopId, @Field("userId") String userId);
//    //=======
//    //            @Field("totalCount") String totalCount, @Field("limitUse") String limitUse,
//    //            @Field("startTime") String startTime, @Field("endTime") String endTime,
//    //            @Field("isCostPrice") String isCostPrice, @Field("isOrderLimit") String isOrderLimit,
//    //            @Field("limitPrice") String limitPrice, @Field("shopId") String shopId,
//    //            @Field("userId") String userId, @Field("type") String type);
//    //>>>>>>> feate
//
//    /**
//     * 优惠券编辑  接通
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/couponset")
//    Observable<HttpResult> editCoupon(@Field("title") String title, @Field("totalCount") String totalCount,
//            @Field("shopId") String shopId, @Field("userId") String userId,
//            @Field("id") String id, @Field("type") String type);
//    //=======
//    //            @Field("totalCount") String totalCount, @Field("limitUse") String limitUse,
//    //            @Field("startTime") String startTime, @Field("endTime") String endTime,
//    //            @Field("isCostPrice") String isCostPrice, @Field("isOrderLimit") String isOrderLimit, @Field("limitPrice") String limitPrice,
//    //            @Field("shopId") String shopId, @Field("userId") String userId, @Field("id") String id, @Field("type") String type);
//    //>>>>>>> feate
//
//    /**
//     * 扫码点餐 列表   接通
//     */
//    @GET("manage/shop/tablelist")
//    Observable<HttpResult<QrcodeOrderResultBean>> getTableList(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize,
//            @Query("shopId") String shopId);
//
//    /**
//     * 扫码点餐 查询服务员
//     */
//    @GET("manage/shop/admin")
//    Observable<QrcodeUserResultBean> selectQrcode(@Query("shopId") String shopId);
//
//    /**
//     * 扫码点餐 创建
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/tableadd")
//    Observable<HttpResult<QrcodeAddBean>> addQrcode(@Field("dinnerNo") String dinnerNo, @Field("serviceId") String serviceId,
//            @Field("serviceName") String serviceName, @Field("shopId") String shopId,
//            @Field("teeFee") String teeFee, @Field("userId") String userId);
//
//    /**
//     * 满减送删除
//     */
//    @GET("manage/shop/meetdelete")
//    Observable<HttpResult> meetDelete(@Query("shopId") String shopId, @Query("userId") String userId);
//
//    /**
//     * 满减送设置
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/setmeet")
//    Observable<HttpResult> meetSeeting(@Field("startTime") String startTime, @Field("endTime") String endTime,
//            @Field("shopId") String shopId, @Field("userId") String userId,
//            @Field("meetData") String meetData);
//
//    /**
//     * 满减送设置
//     */
//    @GET("manage/shop/meet")
//    Observable<FullResultBean> getMeet(@Query("shopId") String shopId);
//
//    /**
//     * 菜品列表
//     */
//    @GET("goods/goods/goods")
//    Observable<MenuDetailResultBean> getGoodsList(@Query("shopId") String shopId, @Query("groupId") String groupId);
//
//    /**
//     * 菜品批量下架
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/goods-take-down")
//    Observable<HttpResult> removeGoods(@Field("goodsId") String goodsId, @Field("opUser") String opUser,
//            @Field("shopId") String shopId);
//
//    /**
//     * 菜品批量上架
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/goods-take-up")
//    Observable<HttpResult> upGoods(@Field("goodsId") String goodsId, @Field("opUser") String opUser,
//            @Field("shopId") String shopId);
//
//    /**
//     * 菜品批量删除
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/goods-remove")
//    Observable<HttpResult> deleteGoods(@Field("goodsId") String goodsId, @Field("opUser") String opUser,
//            @Field("shopId") String shopId);
//
//    /**
//     * 菜品分组列表
//     */
//    @GET("goods/goods/goodsgroup")
//    Observable<MenuGroupResultBean> goodsGroup(@Query("shopId") String shopId);
//
//    /**
//     * 新建菜品分组
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/group-add")
//    Observable<HttpResult> addGroup(@Field("opUser") String opUser, @Field("shopId") String shopId,
//            @Field("title") String title);
//
//    /**
//     * 接口名称 删除分组
//     * 请求类型 post
//     * 请求Url  goods/goods/group-remove
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/group-remove")
//    Observable<HttpResult> removeGroup(@Field("opUser") String opUser, @Field("shopId") String shopId,
//            @Field("groupId") String groupId);
//
//    /**
//     * 接口名称 分组排序
//     * 请求类型 post
//     * 请求Url  goods/goods/group-sort
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/group-sort")
//    Observable<HttpResult> moveGroup(@Field("shopId") String shopId, @Field("groupId") String groupId);
//
//    /**
//     * 接口详情 (id: 219772)     Mock数据
//     * 接口名称 菜品分组详情
//     * 请求类型 get
//     * 请求Url  goods/goods/group-detail
//     */
//    @GET("goods/goods/group-detail")
//    Observable<HttpResult<GroupDetailBean>> getGroupDetail(@Query("groupId") String groupId, @Query("shopId") String shopId);
//
//    /**
//     * 接口名称 编辑菜品分组
//     * 请求类型 post
//     * 请求Url  goods/goods/group-edit
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/group-edit")
//    Observable<HttpResult> editGroup(@Field("opUser") String opUser, @Field("shopId") String shopId,
//            @Field("title") String title, @Field("groupId") String groupId);
//
//    /**
//     * 添加菜品
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/goods-add")
//    Observable<HttpResult> addGood(@Field("content") String content, @Field("group") String group,
//            @Field("picture") String picture, @Field("title") String title, @Field("type") int type,
//            @Field("shopId") String shopId, @Field("opUser") String opUser,
//            @Field("goodsSku") String goodsSku, @Field("shareDishes") int shareDishes);
//
//    /**
//     * 编辑菜品
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/goods-edit")
//    Observable<HttpResult> editGood(@Field("content") String content, @Field("group") String group,
//            @Field("picture") String picture, @Field("title") String title, @Field("type") int type,
//            @Field("shopId") String shopId, @Field("opUser") String opUser,
//            @Field("goodsSku") String goodsSku, @Field("goodsId") int goodsId,
//            @Field("shareDishes") int shareDishes);
//
//    /**
//     * 菜品详情goods/goods/goods-detail
//     */
//    @GET("goods/goods/goods-detail")
//    Observable<GoodsDetailBean> getGoodDetail(@Query("goodsId") int goodsId, @Query("shopId") String shopId);
//
//    /**
//     * 接口详情 (id: 248637)     Mock数据
//     * 接口名称 菜品排序
//     * 请求类型 post
//     * 请求Url  goods/goods/goods-sort
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/goods-sort")
//    Observable<HttpResult> sortGood(@Field("sort") String sort, @Field("shopId") String shopId, @Field("opUser") String opUser);
//
//    /**
//     * 接口名称 店铺所有菜品
//     * 请求类型 get
//     * 请求Url  goods/goods/list
//     */
//    @GET("goods/goods/list")
//    Observable<MenuResultBean> getGoodsList(@Query("shopId") String shopId);
//
//    /**
//     * 接口名称 菜品库
//     * 请求类型 get
//     * 请求Url  goods/goods/all-goods
//     */
//    @GET("goods/goods/all-goods")
//    Observable<AllGoodsResultBean> getAllGoodsList(@Query("shopId") String shopId,
//            @Query("groupId") String groupId);
//
//    /**
//     * 接口名称 批量改分组-移出分组
//     * 请求类型 post
//     * 请求Url  goods/goods/goods-set-group
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/update-group")
//    Observable<HttpResult> changeGroup(@Field("shopId") String shopId, @Field("opUser") String opUser,
//            @Field("groupId") String groupId, @Field("goodsId") String goodsId);
//
//    /**
//     * 接口名称 批量改分组-移出分组
//     * 请求类型 post
//     * 请求Url  goods/goods/goods-set-group
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/goods-set-group")
//    Observable<HttpResult> changeAllGroup(@Field("shopId") String shopId, @Field("opUser") String opUser,
//            @Field("groupId") String groupId, @Field("goodsId") String goodsId);
//
//    /**
//     * goods/goods/remove-groupgoods
//     */
//    @FormUrlEncoded
//    @POST("goods/goods/remove-groupgoods")
//    Observable<HttpResult> deleteGoodsInGroup(@Field("goodsId") String goodsId, @Field("groupId") String groupId,
//            @Field("opUser") String opUser, @Field("shopId") String shopId);
//
//    /**
//     * 接口名称 搜索菜品
//     * 请求类型 get
//     * 请求Url  goods/goods/search
//     */
//    @GET("goods/goods/search")
//    Observable<SearchResultBean> getSearchList(@Query("shopId") String shopId, @Query("keyword") String keyword);
//
//    /**
//     * 接口名称 获取礼包
//     * 请求类型 get
//     * 请求Url  /manage/shop/gift
//     */
//    @GET("manage/shop/gift")
//    Observable<HttpResult<GiftResultBean>> getShopGift(@Query("shopId") String shopId);
//
//    /**
//     * 请求Url  /manage/shop/gift
//     * coupon_id	优惠券ID	number
//     * end_time	结束时间(13位时间戳)	number
//     * goods_id	指定菜品ID(无条件=0)	number	选择指定菜品时才填，默认0
//     * money	条件金额(单位分)	number
//     * shopId	店铺id	number
//     * start_time	开始时间(13位时间戳)	number
//     * type
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/gift")
//    Observable<HttpResult> addShopGift(@Field("coupon_id") String coupon_id, @Field("end_time") String end_time,
//            @Field("goods_id") String goods_id, @Field("money") String money,
//            @Field("shopId") String shopId, @Field("start_time") String start_time,
//            @Field("type") String type, @Field("userId") String userId);
//
//    /**
//     * 请求Url  /manage/shop/gift
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/dele-gift")
//    Observable<HttpResult> deleteShopGift(@Field("shopId") String shopId, @Field("userId") String userId);
//
//    /**
//     * 接口名称 设置店铺推送配置状态
//     * 请求类型 post
//     * 请求Url  manage/shop/set-push       关闭 0，开启 1
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/set-push")
//    Observable<HttpResult> setPush(@Field("shopId") String shopId, @Field("status") String status);
//
//    /**
//     * 接口名称 获取菜系分类
//     * 请求类型 get
//     * 请求Url  /goods/recipe/category
//     */
//    @GET("goods/recipe/category")
//    Observable<HttpResult<CategoryBean>> getCategory();
//
//    /**
//     * 接口名称 菜品列表
//     * 请求类型 get
//     * 请求Url  /goods/recipe/list
//     */
//    @GET("goods/recipe/list")
//    Observable<HttpResult<CategoryGoodBean>> getCategoryGoodsList(@Query("category") String category, @Query("pageNum") int pageNum, @Query("search") String search);
//
//    /**
//     * 接口名称 批量添加菜品图
//     * 请求类型 post
//     * 请求Url  /goods/recipe/batchadd
//     */
//    @FormUrlEncoded
//    @POST("goods/recipe/batchadd")
//    Observable<HttpResult> batchadd(@Field("shopId") String shopId, @Field("userId") String userId, @Field("details") String details);
//
//    /**
//     * 接口名称 获取资质信息
//     * 请求类型 get
//     * 请求Url  platform/shop/get-proof
//     */
//    @GET("platform/shop/get-proof")
//    Observable<HttpResult<QualificationResultBean>> getDetailShop(@Query("shopId") String shopId);
//
//    /**
//     * 接口名称 购买记录
//     * 请求类型 get
//     * 请求Url  manage/shop/order-service-list
//     */
//    @GET("manage/shop/order-service-list")
//    Observable<HttpResult<PayResultBean>> getPayList(@Query("page") int pageNum, @Query("limit") int pageSize,
//            @Query("status") String type, @Query("shop_id") String shopId);
//
//    /**
//     * 接口名称 取消订单
//     * 请求类型 post
//     * 请求Url  manage/shop/service-cancel
//     */
//    @FormUrlEncoded
//    @POST("manage/shop/service-cancel")
//    Observable<HttpResult<String>> serviceCancel(@Field("shop_id") String shopId);
//}
//
