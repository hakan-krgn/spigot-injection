# Spigot Injection

### What is this?

This is a dependency injection library that includes command, configuration,
listener, scheduler and scanner system with auto-injection.
Spigot-injection uses [Guice](https://github.com/google/guice)
for dependency injection.

### How to use?

You can check the example test plugin from here
[Click to go](https://github.com/hakan-krgn/spigot-injection/tree/master/src/test/java/com/hakan/test)

#### 1. Add dependency

#### Maven

```xml

<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.hakan-krgn</groupId>
    <artifactId>spigot-injection</artifactId>
    <version>0.0.9.2</version>
    <scope>compile</scope>
</dependency>
```

#### Gradle

```groovy

repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.hakan-krgn:spigot-injection:0.0.9.2'
}
```

#### 2. Create a main class

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

That's all. Now you can use injection in your classes.

#### 3. Example service

```java

@Service
public class MyService {

    private String serviceMessage;

    @Inject
    public MyService() {
        this.serviceMessage = "Hello World!";
    }

    public void sendMessage(Player player, String message) {
        player.sendMessage(message);
        System.out.println(message);
        System.out.println(this.serviceMessage);
    }
}
```

#### 4. Example command

```java

@Component
public class MyCommand {

    @Inject
    private MyService myService;

    @Command(
            name = "test",
            usage = "/test",
            aliases = {"test2"},
            description = "test command"
    )
    public void execute(CommandSender executor,
                        @Parameter Player target,
                        @Parameter String message,
                        @Parameter int amount) {
        for (int i = 0; i < amount; i++) {
            this.myService.sendMessage(target, message);
        }
    }
}
```

#### 5. Example listener

```java

@Component
public class MyListener {

    private final MyService myService;

    @Inject
    public MyListener(MyService myService) {
        this.myService = myService;
    }

    @Listener
    public void joinEvent(PlayerJoinEvent event) {
        this.myService.sendMessage(event.getPlayer(), "Welcome!");
    }
}
```

#### 6. Example scheduler

```java

@Component
public class MyScheduler {

    private final MyService myService;

    @Inject
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

#### 7. Example configuration

THIS WILL BE UPDATED
