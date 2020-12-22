# ContentProvider本质

  ContentProvider本质是把数据存储在SQLite数据库中。
  不同的数据源有不同的格式，比如短信、通讯录，它们在数据库中就是不同的数据表，但是对于外界的使用者而言，就需要封装成统一的访问方式，比如对于数据集合而言，必须提供增删改查四个方法，于是我们在SQLite之上封装了一层，就是ContentProvider。