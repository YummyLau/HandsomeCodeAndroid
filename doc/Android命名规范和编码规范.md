* [1.语法示例](#1)
* [1.语法示例](#jump)

## Resources files

### Drawable files

* 针对 drawables 的命名规则

|       类型    |         前缀       |          样式               |
|--------------| ------------------|-----------------------------|
| Action bar   | `ab_`             | `ab_stacked.9.png`          |
| Toolbar       | `tb_`             | `tb_stacked.9.png`          |
| Button       | `btn_`	            | `btn_send_pressed.9.png`    |
| Dialog       | `dialog_`         | `dialog_top.9.png`          |
| Divider      | `divider_`        | `divider_horizontal.9.png`  |
| Icon         | `ic_`	            | `ic_star.png`               |
| Menu         | `menu_	`           | `menu_submenu_bg.9.png`     |
| Notification | `notification_`	| `notification_bg.9.png`     |
| Tabs         | `tab_`            | `tab_pressed.9.png`         |

针对 非图片（xmls） 的命名规则

| 类型	       | 前缀             | 样式                     |
|--------------|-----------------|-----------------------------|
| selecotr        | `se_`       | `se_order.xml                 |
| level-list      | `lel_`      | `lel_order.xml`               |
| layer-list      | `lal_`      | `lal_order_focused.9.png`   |
| transition     | `tr_`     | `tr_order.xml`                  |
| color         | `co_`     | `co_order.xml`               |
| shape         | `sh_`     | `sh_order.xml`           |
| scale         | `sc_`     | `sc_order.xml`           |
| clip          | `cl_`     | `cl_order.xml`           |
| rotate        | `ro_`     | `ro_order.xml`           |
| animation-list| `al_`     | `al_order.xml`           |
| inset         | `in_`     | `in_order.xml`           |

* 针对 icons 的命名规则

| 类型                             | 前缀                | 样式                       |
| --------------------------------| ----------------   | ---------------------------- |
| Icons（常规图标）                 | `ic_`              | `ic_star.png`                |
| Launcher icons                  | `ic_launcher`      | `ic_launcher_calendar.png`   |
| Menu icons and Action Bar icons | `ic_menu`          | `ic_menu_archive.png`        |
| Status bar icons                | `ic_stat_notify`   | `ic_stat_notify_msg.png`     |
| Tab icons                       | `ic_tab`           | `ic_tab_recent.png`          |
| Dialog icons                    | `ic_dialog`        | `ic_dialog_info.png`         |

* 针对 selectors 的命名规则

| 类型	       | 后缀             | 样式                     |
|--------------|-----------------|-----------------------------|
| Normal       | `_normal`       | `btn_order_normal.9.png`    |
| Pressed      | `_pressed`      | `btn_order_pressed.9.png`   |
| Focused      | `_focused`      | `btn_order_focused.9.png`   |
| Disabled     | `_disabled`     | `btn_order_disabled.9.png`  |
| Selected     | `_selected`     | `btn_order_selected.9.png`  |



### Layout files

常见顶级容器命名规则

| Component        | Class Name             | Layout Name                   |
| ---------------- | ---------------------- | ----------------------------- |
| Activity         | `UserProfileActivity`  | `activity_user_profile.xml`   |
| Fragment         | `SignUpFragment`       | `fragment_sign_up.xml`        |
| Dialog           | `ChangePasswordDialog` | `dialog_change_password.xml`  |
| Toast            | `CommonToast`          | `toast_common.xml`            |
| AdapterView item | ---                    | `item_person.xml`             |
| Partial layout   | ---                    | `partial_stats_bar.xml`       |

常见自定义布局命名规则

| widget            | Class Name             | 前缀                   |
| ---------------- | ---------------------- | ----------------------------- |
| LinearLayout     | `LinearLayout`          | `ll_`         |
| RelativeLayout    | `RelativeLayout`       | `rl`          |
| ConstraintLayout  | `ConstraintLayout`     | `cl`          |
| ListView          | `ListView`              | `lv`         |
| ScollView         | `ScollView`             |`sv`          |
| TextView          |`TextView`               | `tv`        |
| ScollView         | `ScollView`             | `btn`       |
| Button            | `Button`                | `v`         |
| CheckBox          | `CheckBox`              | `cb`        |
| EditText          | `EditText`              | `et`        |


### colors 中针对 非图片（xmls）的 映射规则，比如selecotr

| drawable	       |    状态                   |       color作用域            | 样式                           |
|--------------    |------------------------- |-----------------------------|--------------------------------|
| selecotr         |  `press`                 |      `solid`                | 'se_order_pressed_solid'      |
| selecotr         |  `focus`                 |      `stroke`                | 'se_order_focus_stroke'      |

<h2 id='1'>1.语法示例</h2>
<h2 id='jump'>1.语法示例</h2>