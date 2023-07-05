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
    <dependency>
        <groupId>com.github.hakan-krgn</groupId>
        <artifactId>spigot-injection</artifactId>
        <version>0.0.9.5</version>
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
    implementation 'com.github.hakan-krgn:spigot-injection:0.0.9.5'
}
```

## Usage

### 1. Create a main class

We need to create a main class to start the injection process. We can use @Scanner annotation to specify the package
that will be scanned.

```java
// this package will be scanned
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

Now you can use injection in your classes. Now we can create our service class to first. This service will
be our manager class.

```java
//this is the service class of the plugin
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

We can use @Command and @Subcommand annotations to create commands. Also we can use @CommandParam annotation to get
command parameters. We can use @Executor annotation to specify the executor of the command.

```java
//we can use @Command and @Subcommand annotations to create commands
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

Now we can create our configuration class. We can use @ConfigFile annotation to create configurations. Also we can use
@ConfigValue annotation to get values from the configuration.

```java
//we can use @ConfigFile annotation to create configurations
//also we can use @ConfigValue annotation to get values from the configuration
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
    Integer getRepeat();
}
```

### 5. Example repository

Now we can create our repository class. We can use @Repository annotation to create repositories. Also we can use
@Query annotation to create specific queries. We can use @QueryParam annotation to specify the parameters of the query.

```java
//we can use @Repository annotation to create repositories
//also we can specify the credentials in the @Repository annotation

//if DbCredential has an instance, you don't have to
//specify the credentials in the @Repository annotation.
@Repository(
        username = "root",
        password = "admin",
        driver = "com.mysql.cj.jdbc.Driver",
        url = "jdbc:mysql://localhost:3306",

        id = Integer.class,
        entity = User.class,

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

We can use @EventListener annotation to create listeners. These listeners will be registered automatically to server.

```java
//we can use @EventListener annotation to create listeners

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

We can use @Scheduler annotation to create schedulers. These schedulers will be started automatically. Also schedulers
uses spigot scheduler system.

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

If you want to see usage of the library, you can check
the [example plugin](https://github.com/hakan-krgn/spigot-injection/tree/master/example-plugin)

## License

This project is licensed under the MIT License - see
the [LICENSE](https://github.com/hakan-krgn/spigot-injection/blob/master/LICENSE) file for details.
