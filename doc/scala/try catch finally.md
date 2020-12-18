"_" wildcard operator to catch any other exceptions that might occur

```scala
try {
  // your scala code here
} catch {
  case foo: FooException => handleFooException(foo)
  case bar: BarException => handleBarException(bar)
  case _: Throwable => println("Got some other kind of exception")
} finally {
  // your scala code here, such as to close a database connection
}
```

naming your exception variable instead of using the wildcard, which you can then refer to in your own code, something like this:

```scala
try {
  // your scala code here
} catch {
  case foo: FooException => handleFooException(foo)
  case bar: BarException => handleBarException(bar)
  
  // handling any other exception that might come up
  case unknown => println("Got this unknown exception: " + unknown)
} finally {
  // your scala code here, such as to close a database connection
}
```
Printing a Scala exception's stack trace
```scala
def runAppleScriptCommand(c: AppleScriptCommand) {
  val scriptEngineManager = new ScriptEngineManager
  val appleScriptEngine = scriptEngineManager.getEngineByName("AppleScript")
  try {
    appleScriptEngine.eval(c.command)
  } catch {
    case e: ScriptException => e.printStackTrace
  }
}
```























