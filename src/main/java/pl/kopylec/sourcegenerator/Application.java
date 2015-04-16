package pl.kopylec.sourcegenerator;

import pl.kopylec.sourcegenerator.core.java.AbstractMethod;
import pl.kopylec.sourcegenerator.core.java.AccessModifier;
import pl.kopylec.sourcegenerator.core.java.Annotation;
import pl.kopylec.sourcegenerator.core.java.AnnotationElement;
import pl.kopylec.sourcegenerator.core.java.Constant;
import pl.kopylec.sourcegenerator.core.java.Constructor;
import pl.kopylec.sourcegenerator.core.java.EnumConstructor;
import pl.kopylec.sourcegenerator.core.java.EnumValue;
import pl.kopylec.sourcegenerator.core.java.Field;
import pl.kopylec.sourcegenerator.core.java.Import;
import pl.kopylec.sourcegenerator.core.java.InterfaceConstant;
import pl.kopylec.sourcegenerator.core.java.InterfaceMethod;
import pl.kopylec.sourcegenerator.core.java.JavaAnnotation;
import pl.kopylec.sourcegenerator.core.java.JavaClass;
import pl.kopylec.sourcegenerator.core.java.JavaEnum;
import pl.kopylec.sourcegenerator.core.java.JavaInterface;
import pl.kopylec.sourcegenerator.core.java.Method;
import pl.kopylec.sourcegenerator.core.java.Modifier;
import pl.kopylec.sourcegenerator.core.java.Parameter;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        JavaClass javaClass = new JavaClass("pl.test", "Test", "C:/")
                .setSuperClass("Object")
                .addImport(new Import("pl.test.Chuj"))
                .addAnnotation(
                        new Annotation("Component")
                                .addAttribute("name", "\"bean\"")
                                .addAttribute("value", "HAHAHA"))
                .addAnnotation(new Annotation("Path"))
                .addImplementedInterface("Serializable")
                .addImplementedInterface("Inter")
                .addField(new Field(AccessModifier.PROTECTED, Modifier.STATIC, "String", "message"))
                .addField(
                        new Field("int", "age")
                                .setValue("123")
                                .addAnnotation(new Annotation("Column"))
                                .addAnnotation(
                                        new Annotation("Autowired")
                                                .addAttribute("required", "false")))
                .addField(new Constant("double", "PRICE", "10.99"))
                .addMethod(new Constructor("Test"))
                .addMethod(new Constructor("Test")
                                .addParameter(new Parameter("Object", "o"))
                )
                .addMethod(new Method(AccessModifier.PRIVATE, Modifier.FINAL, "String", "getMessage")
                                .addAnnotation(new Annotation("Secured"))
                )
                .addMethod(new Method("int", "calculate")
                                .addParameter(new Parameter("int", "a")
                                                .addAnnotation(new Annotation("NotNull"))
                                                .addAnnotation(new Annotation("Valid"))
                                )
                                .addParameter(new Parameter("int", "b", Modifier.FINAL))
                                .setBody("\tint sum = a + b;\n\treturn sum;")
                )
                .addMethod(new AbstractMethod("double", "test"));
        javaClass.createSourceFile();

        new JavaInterface("pl.int.chuj", "Service", "C:/")
                .addAnnotation(new Annotation("Path"))
                .addImport(new Import("pl.plp.pl.pl.Dupa"))
                .addSuperInterface("Serializable")
                .addSuperInterface("Context")
                .addMethod(new InterfaceMethod("String", "getText"))
                .addMethod(new InterfaceMethod(Modifier.DEFAULT, "int", "add")
                                .addParameter(new Parameter("int", "haha"))
                                .setBody("\treturn 1;")
                )
                .addConstant(new InterfaceConstant("long", "ID", "666"))
                .createSourceFile();

        new JavaEnum("pl.enum", "MyEnum", "C:/")
                .addAnnotation(new Annotation("Path"))
                .addImport(new Import("pl.plp.pl.pl.Dupa"))
                .addImplementedInterface("Serializable")
                .addMethod(new Method("String", "getText"))
                .addMethod(new Method("int", "add")
                                .addParameter(new Parameter("int", "haha"))
                                .setBody("\treturn 1;")
                )
                .addMethod(new EnumConstructor("MyEnum")
                                .setBody("\tthis.dupa = 123;")
                )
                .addValue(new EnumValue("FIRST")
                                .addArgument("123")
                                .addArgument("\"text\"")
                )
                .addValue(new EnumValue("SECOND"))
                .createSourceFile();

        System.out.println(javaClass);

        JavaAnnotation javaAnnotation = new JavaAnnotation("pl.pl", "Annot", "C:/")
                .addAnnotation(new Annotation("Test"))
                .addImport(new Import("pl.imp.core.Dupa"))
                .addElement(new AnnotationElement("String", "text")
                                .addParameter(new Parameter("int", "i"))
                                .setDefaultValue("1")
                );
        javaAnnotation.createSourceFile();
        System.out.println(javaAnnotation);
    }
}
