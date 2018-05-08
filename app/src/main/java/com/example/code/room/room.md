### 表结构的设计
Room 构建表结构是基于 `JavaBeans` 的约定，对于需要储存数据库的每一个标量，Room 需要有权限访问到。所以，对于每个需要存储的变量，要么声明为 `Public` 类型，要么提供 `getter` 和 `setter` 方法。
对于表设计所用到的注解有:
* `@Entity` 默认情况下，为该注释的类的每一个可访问变量生成列并构建一张表，默认取类名做为表名
* `@ColumnInfo` 指明某个属性的列名，默认取该属性名作为列明
* `@Ignore` 表示不对该注释的变量进行数据库存储
* `@PrimaryKey` 每一个entity必须至少有一个属性作为主键，如果希望为entity设置自增ID，则autoGenerate设置为true。当该注解作用于 @Embedded，则该类的所有属性会作为复合键。
* `@Index` 声明索引，声明索引的属性或者属性组需要唯一存在，unique设置为true

类之间的关联
在Room 中，禁止实体对象相互引用。（未完），尽管如此，依然还是可以使用外键来关联多个实体。
* `@foreignKeys` 能够让引用的实体外键属性更新时自动更新childColumns属性
   外键关联删除？？？？

类之间的嵌套
* `@Embedded` 一个表包含另一个包的属性，如果包含的属性存在属性列名冲突，则可以使用prefix来为subObject生命。
如果subObject中包含的主键，则主体类不会把包含的主键作为自己的主键

### 数据库升级
https://developer.android.com/training/data-storage/room/migrating-db-versions









### 数据库注意点
1. 默认情况下，不允许在主线程查询，除非调用 allowMainThreadQueries()
 Asynchronous queries—queries that return instances of LiveData or Flowable—are exempt from this rule because they asynchronously run the query on a background thread when needed.

1. 关于sqlite冲突处理方案
针对SQL的onconfig，存在 INSERT 和 UPDATE 指令中，UIban使用 OR 代替 ON CONFLICT

