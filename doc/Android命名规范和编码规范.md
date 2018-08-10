* [1. Android 资源文件命名](#Android资源文件命名)
    * [1.1 layout 资源命名](#layout资源命名)
    * [1.2 drawable 资源命名](#drawable资源命名)
    * [1.3 color 资源命名](#colors资源命名)
    * [1.4 dimen 资源命名](#dimen资源命名)
    * [1.5 animation 资源命名](#animation资源命名)
    * [1.6 style 资源命名](#style资源命名)
    * [1.7 string 资源命名](#string资源命名)
    * [1.8 id 资源命名](#id资源命名)
* [2. Android 编码规范](#Android编码规范)

<h3 id='Android资源文件命名'>1. Android 资源文件命名</h3>

<h4 id='layout资源命名'>1.1 layout 资源命名</h4>
以 *模块名_Componet类型名_业务功能描述* 作为命名规则。 

| 类型             | 类型样例               | 命名                                          |
| ---------------- | ---------------------- | --------------------------------------------- |
| Activity         | `UserProfileActivity`  | `app_activity_user_profile.xml`            |
| Fragment         | `SignUpFragment`       | `app_fragment_sign_up.xml`                 |
| Dialog           | `ChangePasswordDialog` | `app_dialog_change_password.xml`           |
| Toast            | `CusToast`             | `app_toast_{xxx}.xml`                      |
| View             | `RecyclerView`         | `app_recyclerview_{xxx}.xml`               |
| ListView item    | `CusItem`              | `app_item_{xxx}.xml`                       |
| RecycleView VH   | `CusViewHolder`        | `app_holder_{xxx}.xml`                     |
| CusWidget        | `ExpandableTextView`   | `app_widget_expandable_textview.xml`       |


<h4 id='drawable资源命名'>1.2 drawable 资源命</h4>

* 图片类 Drawable，只做 xxhdpi 资源，其他分辨率通过该资源缩放。以 *模块名_可选ic_业务功能描述_状态限定* 作为命名规则。 

| 常见类型                    | 规则                                        | 状态     |  命名                           | 
| ----------------------------| --------------------------------------------|----------|  -------------------------------|
|Icons 类                     | `{module}_ic_{xxx}_{status}`                |`——`      | `——`                            |
|(常规图标) Launcher icons    | `{module}_ic_launcher_{xxx}_{status}`       |`Normal`  | `app_ic_launcher_calendar.png`  |
|(常规图标) Menu icons        | `{module}_ic_menu_{xxx}_{status}`           |`Normal`  | `app_ic_menu_archive.png`       |
|(常规图标) Action Bar icons  | `{module}_ic_action_{xxx}_{status}`         |`Normal`  | `app_ic_menu_archive.png`       |
|(常规图标) Status bar icons  | `{module}_ic_statusbar_{xxx}_{status}`      |`Normal`  | `app_ic_stat_notify_msg.png`    |
|(常规图标) Tab icons         | `{module}_ic_tab_{xxx}_{status}`            |`selected`| `app_ic_tab_recent_selected.png`|
|(常规图标) Dialog icons      | `{module}_ic_dialog_{xxx}_{status}`         |`disable` | `app_ic_dialog_info_disable.png`|
|(普通图标) some icons        | `{module}_ic_{xxx}_{status}`                |`focused` | `app_ic_btn_send_focused.png`   |
|其他类（背景图/大图/分割等   | `{module}_{xxx}_{status}`                   |`pressed` | `app_tv_background_pressed.png` |


* XML自定义 Drawable，以 *模块名_资源类型_业务功能描述* 作为命名规则。 

| 类型	         | 规则                   | 命名                     |
|----------------|------------------------|--------------------------|
| selecotr       | `{module}_se_{xxx}`    | `app_se_btn_send.xml`    |
| level-list     | `{module}_lel_{xxx}`   | `app_lel_btn_send.xml`   |
| layer-list     | `{module}_lal_{xxx}`   | `app_lal_btn_send.xml`   |
| transition     | `{module}_tr_{xxx}`    | `app_tr_btn_send.xml`    |
| color          | `{module}_co_{xxx}`    | `app_app_btn_send.xml`   |
| shape          | `{module}_sh_{xxx}`    | `app_sh_btn_send.xml`    |
| scale          | `{module}_sc_{xxx}`    | `app_sc_btn_send.xml`    |
| clip           | `{module}_cl_{xxx}`    | `app_cl_btn_send.xml`    |
| rotate         | `{module}_ro_{xxx}`    | `app_ro_btn_send.xml`    |
| animation-list | `{module}_al_{xxx}`    | `app_al_btn_send.xml`    |
| inset          | `{module}_in_{xxx}`    | `app_in_btn_send.xml`    |


<h4 id='colors资源命名'>1.3 color 资源命名</h4>
color资源使用 `#AARRGGBB` 格式，如没有透明度可省略前两位，`RRGGBB` 如有通过字母标示则统一使用大写标示。
```
//bad
<color name="app_btn_send_pressed">#2a344f</color>
//good
<color name="app_btn_send_pressed">#2A344F</color>
```
color颜色资源也区分一下两种：

* `res/values/colors` 引用资源，以 *模块名_业务功能描述_状态_作用域* 作为命名规则。

| 类型	               | 规则                                           | 状态      |作用域 |命名                          |
|----------------------|------------------------------------------------|-----------|-------|------------------------------|
| `res/values/colors`  | `{module}_{xxx}_{status}_{area}`| `pressed` |`solid`| `app_btn_send_pressed_solid` |


* `res/color` XML资源，以 *模块名_资源类型_业务功能描述* 作为命名规则。

| 类型	               | 规则                            | 类型      | 命名            |
|----------------------|---------------------------------|-----------|-----------------|
| `res/color`          | `{module}_{类型}_{xxx}`         | `shape`   |`app_sh_btn_send`|   

<h4 id='dimen资源命名'>1.4 dimen 资源命</h4>
dimen 资源以 *模块名_业务功能描述* 作为命名规则。

```
<dimen name="app_home_page_height">175dp</dimen>
```

<h4 id='animation资源命名'>1.5 animation 资源命</h4>
style 资源以 *anim_业务功能描述_[方向|序号]* 作为命名规则。

```
//activity 退出
anim_activity_out.xml
//loading帧
anim_loading_grey_01.xml
anim_loading_grey_02.xml
anim_loading_grey_03.xml
```

<h4 id='style资源命名'>1.6 style 资源命</h4>
style 资源以 *父 style 名称.当前 stype 名称* 作为命名规则。

```
<style name="Theme.AppCompat.Light.DarkActionBar.AppTheme">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>
 </style>
```

<h4 id='string资源命名'>1.7 string 资源命</h4>
string 资源以 *模块名_业务功能描述* 作为命名规则。

```
<string name="app_crash_tip">程序异常退出</string>
```

<h4 id='id资源命名'>1.8 id 资源命</h4>
id 资源以 *控件缩写名_业务功能描述* 作为命名规则，常见的 View 组件缩写如下

| 类型	               | 缩写                       | 命名                  |
|----------------------|----------------------------|-----------------------|
| `View`               | `vw`                       | `vw_send`             |
| `TextView`           | `tv`                       | `tv_show_nick`        | 
| `EditText`           | `et`                       | `et_input_username`   | 
| `ImageButton`        | `ib`                       | `ib_send`             | 
| `Button`             | `btn`                      | `btn_send`            | 
| `ImageView`          | `iv`                       | `iv_send`             |  
| `ListView`           | `lv`                       | `lv_user_list`        | 
| `GridView`           | `gv`                       | `gv_images`           |  
| `ProgressBar`        | `pb`                       | `pb_loading`          |  
| `SeekBar`            | `sb`                       | `sb_volume`           |  
| `RadioButtion`       | `rb`                       | `rb_color`            |  
| `CheckBox`           | `cb`                       | `cb_sex`              |  
| `ScrollView`         | `sv`                       | `sv_user_info`        |  
| `LinearLayout`       | `ll`                       | `ll_user_info`        |  
| `FrameLayout`        | `fl`                       | `fl_user_info`        |  
| `RelativeLayout`     | `rl`                       | `rl_user_info`        |  
| `WebView`            | `wb`                       | `wb_articel`          |  
| `VideoView`          | `vv`                       | `vv_video`            |  
| `Recyclerview`       | `rv`                       | `rv_user_list`        |  
| `Spinner`            | `spn`                      | `spn_color`           | 
| `ToggleButton`       | `tb`                       | `tb_sex`              |  
| `自定义View`         | `cus`                      | `cus_send`            | 

   



