# Source Gen-X
[![Build Status](https://travis-ci.org/mkopylec/source-gen-x.svg?branch=master)](https://travis-ci.org/mkopylec/source-gen-x)
[![Coverage Status](https://coveralls.io/repos/mkopylec/source-gen-x/badge.svg?branch=master)](https://coveralls.io/r/mkopylec/source-gen-x?branch=master)

Source code and files generator with an intuitive API written in Java.
With this tool you can create your sources with a minimal learning curve. 

The goal of the project is to create natural, easy to use and self-documented Java API.

## Usage
Every class that represents a model of source file is a subclass of `SourceFile`.
To create a physical file use `createSourceFile()` method.
To create a string representation of file content invoke `toString()`.
The generated source code often needs some kind of reformatting.

The following examples are **not a complete overview** of available methods.
They are just a simple demonstration of how to use the Source Gen-X tool.

### Generating a Java class
Creating a sample main class:
    
```java
Constant constant = new Constant("String", "GREETING", "\"Hello World!\"");

Method main = new Method(AccessModifier.PUBLIC, Modifier.STATIC, "void", "main")
        .addParameter(new Parameter("String[]", "args"))
        .setBody("\tSystem.out.println(GREETING);");

JavaClass mainClass = new JavaClass("sourcegenx.demo", "Application", userHome + "/demo/src/main/java/sourcegenx/demo")
        .addField(constant)
        .addMethod(main);

mainClass.createSourceFile();
```
    
Output Application.java:

    package sourcegenx.demo;


    public class Application   {

        public static final String GREETING = "Hello World!";

        public static void main(String[] args) {
    	    System.out.println(GREETING);
        }
    }

### Generating a Java interface
Creating a sample DAO interface:

```java
Import imp = new Import("sourcegenx.demo.entities.User");
       
InterfaceMethod findMethod = new InterfaceMethod("User", "findById")
        .addParameter(new Parameter("long", "id"));
       
JavaInterface dao = new JavaInterface("UserDao", "sourcegenx.demo", "UserDao<User>", userHome + "/demo/src/main/java/sourcegenx/demo")
        .addImport(imp)
        .addMethod(findMethod);
        
dao.createSourceFile();
```
        
Output UserDao.java:

    package sourcegenx.demo;

    import sourcegenx.demo.entities.User;

    public interface UserDao<User>  {


         User findById(long id);
    }

### Generating a Java enum
Creating a sample cardinal directions enum:

```java
JavaEnum directions = new JavaEnum("sourcegenx.demo", "CardinalDirection", userHome + "/demo/src/main/java/sourcegenx/demo")
        .addValue(new EnumValue("NORTH"))
        .addValue(new EnumValue("WEST"))
        .addValue(new EnumValue("SOUTH"))
        .addValue(new EnumValue("EAST"));
        
directions.createSourceFile();
```
    
Output CardinalDirection.java:

    package sourcegenx.demo;


    public enum CardinalDirection  {

        NORTH,
        WEST,
        SOUTH,
        EAST;


    }
    
### Generating a Java annotation
Creating a sample secured annotation:

```java
Annotation target = new Annotation("Target")
        .addAttribute("value", "METHOD");

Annotation retention = new Annotation("Retention")
        .addAttribute("value", "RUNTIME");

AnnotationElement allowedRoles = new AnnotationElement("String[]", "allowedRoles")
        .setDefaultValue("{\"ADMIN\", \"CUSTOMER\"}");

JavaAnnotation secured = new JavaAnnotation("sourcegenx.demo", "Secured", userHome + "/demo/src/main/java/sourcegenx/demo")
        .addImport(new Import("java.lang.annotation.Retention"))
        .addImport(new Import("java.lang.annotation.Target"))
        .addImport(new Import(Modifier.STATIC, "java.lang.annotation.ElementType.METHOD"))
        .addImport(new Import(Modifier.STATIC, "java.lang.annotation.RetentionPolicy.RUNTIME"))
        .addAnnotation(target)
        .addAnnotation(retention)
        .addElement(allowedRoles);

secured.createSourceFile();
```
    
Output Secured.java:

    package sourcegenx.demo;

    import java.lang.annotation.Retention;
    import java.lang.annotation.Target;
    import static java.lang.annotation.ElementType.METHOD;
    import static java.lang.annotation.RetentionPolicy.RUNTIME;

    @Target(METHOD)
    @Retention(RUNTIME)
    public @interface Secured {

        String[] allowedRoles() default {"ADMIN", "CUSTOMER"};
    }

## More examples
See test specifications for more samples:

- [Java class spec](https://github.com/mkopylec/source-gen-x/blob/master/src/test/groovy/pl/allegro/tech/sourcegenx/core/java/JavaClassSpec.groovy)
- [Java interface spec](https://github.com/mkopylec/source-gen-x/blob/master/src/test/groovy/pl/allegro/tech/sourcegenx/core/java/JavaInterfaceSpec.groovy)
- [Java enum spec](https://github.com/mkopylec/source-gen-x/blob/master/src/test/groovy/pl/allegro/tech/sourcegenx/core/java/JavaEnumSpec.groovy)
- [Java annotation spec](https://github.com/mkopylec/source-gen-x/blob/master/src/test/groovy/pl/allegro/tech/sourcegenx/core/java/JavaAnnotationSpec.groovy)

## License
Source Gen-X is published under [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).