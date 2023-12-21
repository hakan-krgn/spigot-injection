# Spigot Injection

## What is this library?

This is a dependency injection library that includes command, configuration, database, listener, scheduler and scanner
system with auto-injection. Spigot-injection
uses [Basic Dependency Injection](https://github.com/hakan-krgn/basic-dependency-injection) for dependency injection.

## Getting Started

You can use this library with maven or gradle. You just need to add the dependency to your pom.xml or build.gradle file
to use it. Then you can create your main class and start the injection process.

### Prerequisites

- Spigot 1.8.8 or above
- Java Development Kit (JDK) 8 or above

### Installation

#### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <!-- Core of dependency injection -->
    <dependency>
        <groupId>com.github.hakan-krgn.spigot-injection</groupId>
        <artifactId>injection-core</artifactId>
        <version>0.1.5.0</version>
        <scope>compile</scope>
    </dependency>

    <!-- Annotation based config support -->
    <dependency>
        <groupId>com.github.hakan-krgn.spigot-injection</groupId>
        <artifactId>injection-config</artifactId>
        <version>0.1.5.0</version>
        <scope>compile</scope>
    </dependency>

    <!-- Annotation based command support -->
    <dependency>
        <groupId>com.github.hakan-krgn.spigot-injection</groupId>
        <artifactId>injection-command</artifactId>
        <version>0.1.5.0</version>
        <scope>compile</scope>
    </dependency>

    <!-- Annotation based listener support -->
    <dependency>
        <groupId>com.github.hakan-krgn.spigot-injection</groupId>
        <artifactId>injection-listener</artifactId>
        <version>0.1.5.0</version>
        <scope>compile</scope>
    </dependency>

    <!-- Annotation based scheduler support -->
    <dependency>
        <groupId>com.github.hakan-krgn.spigot-injection</groupId>
        <artifactId>injection-scheduler</artifactId>
        <version>0.1.5.0</version>
    <scope>compile</scope>

    <!-- Native database support -->
    <dependency>
        <groupId>com.github.hakan-krgn.spigot-injection</groupId>
        <artifactId>injection-database-native</artifactId>
        <version>0.1.5.0</version>
        <scope>compile</scope>
    </dependency>

    <!-- Hibernate database support (you need to add hibernate dependency to your project as extra) -->
    <dependency>
        <groupId>com.github.hakan-krgn.spigot-injection</groupId>
        <artifactId>injection-database-hibernate</artifactId>
        <version>0.1.5.0</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

#### Gradle

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    //Core of dependency injection
    implementation 'com.github.hakan-krgn.spigot-injection:injection-core:0.1.5.0'

    //Annotation based config support
    implementation 'com.github.hakan-krgn.spigot-injection:injection-config:0.1.5.0'

    //Annotation based command support
    implementation 'com.github.hakan-krgn.spigot-injection:injection-command:0.1.5.0'

    //Annotation based listener support
    implementation 'com.github.hakan-krgn.spigot-injection:injection-listener:0.1.5.0'

    //Annotation based scheduler support
    implementation 'com.github.hakan-krgn.spigot-injection:injection-scheduler:0.1.5.0'

    //Native database support
    implementation 'com.github.hakan-krgn.spigot-injection:injection-database-native:0.1.5.0'

    //Hibernate database support (you need to add hibernate dependency to your project as extra)
    implementation 'com.github.hakan-krgn.spigot-injection:injection-database-hibernate:0.1.5.0'
}
```

## Usage

### 1. Create a main class

We need to create a main class to start the injection process. We can use `@Scanner` annotation to specify the package
that will be scanned.

```java
@Scanner("com.hakan.test")
public class MyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // this will start the injection process
        SpigotBootstrap.run(this);
    }
}
```

### 2. Example service

Now you can use injection in your classes. Now we can create our service class first. This service will be our manager
class.

```java
@Service
public class MyService {

    private String serviceMessage;

    @Autowired
    public MyService() {
        this.serviceMessage = "Hello World!";
    }

    @PostConstruct
    public void init() {
        System.out.println("MyService is initialized!");
        System.out.println(this.serviceMessage);
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(message);
        System.out.println(message);
        System.out.println(this.serviceMessage);
    }
}
```

### 3. Example command

We can use `@Command` and `@Subcommand` annotations to create commands. We can use `@CommandParam` annotation to get
command parameters. We can use `@Executor` annotation to specify the executor of the command.

```java
@Command(
        name = "mycommand",
        aliases = {"mc"},
        description = "My command",
        usage = "/mycommand <target> <message> <amount>",
)
public class MyCommand {

    private final MyService myService;

    @Autowired
    public MyCommand(MyService myService) {
        this.myService = myService;
    }

    @Subcommand(
            permission = "mycommand.use",
            permissionMessage = "You don't have permission to use this command!",
    )
    public void execute(@Executor CommandSender executor,
                        @CommandParam Player target,
                        @CommandParam String message,
                        @CommandParam int amount) {
        for (int i = 0; i < amount; i++) {
            this.myService.sendMessage(target, message);
        }
    }
}
```

### 4. Example configuration

Now we can create our configuration class. We can use `@ConfigFile` annotation to create configurations. We can use
`@ConfigValue` annotation to get values from the configuration.

```java
@ConfigFile(
        type = ContainerType.YAML,
        resource = "config.yml",
        path = "plugins/MyPlugin/config.yml",

        reloadTimer = @ConfigTimer(
                enabled = true,
                delay = 10,
                period = 10,
                async = true,
                timeUnit = TimeUnit.SECONDS
        ),

        saveTimer = @ConfigTimer(
                enabled = true,
                delay = 10,
                period = 10,
                async = true,
                timeUnit = TimeUnit.SECONDS
        )
)
public interface MyConfig extends BaseConfiguration {

    @ConfigValue("settings.message")
    String getMessage();

    @ConfigValue("settings.repeat")
    Integer getRepeat(Integer defaultValue);
}
```

### 5. Example repository

Now we can create our repository class. We can use @Repository annotation to create repositories. Also, we can use
`@Query` annotation to create specific queries. `@QueryParam` annotation is used to specify the parameters of the query.
Also, we can specify the credentials in the @Repository annotation, but if DbCredential has an instance, you don't have
to specify the credentials in the @Repository annotation.

```java
@Repository(
        username = "root",
        password = "admin",
        driver = "com.mysql.cj.jdbc.Driver",
        url = "jdbc:mysql://localhost:3306",

        queries = {
                "create database hakan;",
        }
)
public interface MyRepository extends JpaRepository<Integer, User> {

    @Query("select u from User u where u.name = :name and u.credential.email = :email")
    User findByNameAndEmail(@QueryParam("name") String name,
                            @QueryParam("email") String email);
}
```

### 6. Example listener

We can use `@EventListener` annotation to create listeners. These listeners will be registered automatically to server.

```java
@Component
public class MyListener {

    private final MyService myService;

    @Autowired
    public MyListener(MyService myService) {
        this.myService = myService;
    }

    @EventListener
    public void joinEvent(PlayerJoinEvent event) {
        this.myService.sendMessage(event.getPlayer(), "Welcome!");
    }
}
```

### 7. Example scheduler

We can use `@Scheduler` annotation to create schedulers. These schedulers will be started automatically. Also, the
scheduler system of the library uses the spigot's system.

```java
@Component
public class MyScheduler {

    private final MyService myService;

    @Autowired
    public MyScheduler(MyService myService) {
        this.myService = myService;
    }

    @Scheduler(
            delay = 1,
            period = 5,
            async = true,
            timeUnit = TimeUnit.MINUTES
    )
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.myService.sendMessage(player, "Hello!");
        }
    }
}
```

### Example Plugin

If you want to see usage of the library, you can check some of the plugin examples.

User plugin -> [Click to go](https://github.com/hakan-krgn/spigot-injection/tree/master/plugin-examples/user-plugin)

Full plugin -> [Click to go](https://github.com/hakan-krgn/spigot-injection/tree/master/plugin-examples/full-plugin)

## License

This project is licensed under the MIT License. You can check
out [LICENSE](https://github.com/hakan-krgn/spigot-injection/blob/master/LICENSE) file for details.
