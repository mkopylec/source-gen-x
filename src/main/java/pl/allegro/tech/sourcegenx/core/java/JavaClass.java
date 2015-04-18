package pl.allegro.tech.sourcegenx.core.java;

import org.stringtemplate.v4.ST;
import pl.allegro.tech.sourcegenx.core.SourceFile;

import java.util.ArrayList;
import java.util.List;

import static pl.allegro.tech.sourcegenx.core.SourceFileType.JAVA_CLASS;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.ABSTRACT;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.FINAL;
import static pl.allegro.tech.sourcegenx.core.java.Modifier.NONE;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfInstanceOf;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNotOneOf;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

@SuppressWarnings("unchecked")
public class JavaClass extends SourceFile {

    private final String packageName;
    private final List<Import> imports = new ArrayList<>();
    private final List<Annotation> annotations = new ArrayList<>();
    private final AccessModifier accessModifier;
    private final Modifier modifier;
    private final String className;
    private String superClass;
    private final List<String> implementedInterfaces = new ArrayList<>();
    private final List<Field> fields = new ArrayList<>();
    private final List<Method> methods = new ArrayList<>();

    public JavaClass(String packageName, String className, String directory) {
        this(className, packageName, className, directory, PUBLIC, NONE);
    }

    public JavaClass(String packageName, String className, String directory, AccessModifier accessModifier) {
        this(className, packageName, className, directory, accessModifier, NONE);
    }

    public JavaClass(String packageName, String className, String directory, Modifier modifier) {
        this(className, packageName, className, directory, PUBLIC, modifier);
    }

    public JavaClass(String fileName, String packageName, String className, String directory) {
        this(fileName, packageName, className, directory, PUBLIC, NONE);
    }

    public JavaClass(String fileName, String packageName, String className, String directory, AccessModifier accessModifier, Modifier modifier) {
        super(fileName, directory, JAVA_CLASS);
        failIfBlank(packageName, "Empty Java class package name");
        failIfBlank(className, "Empty Java class name");
        failIfNull(accessModifier, "Empty Java class access modifier");
        failIfNotOneOf(accessModifier, "Invalid Java class access modifier: " + accessModifier, PUBLIC, PACKAGE_PRIVATE);
        failIfNull(modifier, "Empty Java class modifier");
        failIfNotOneOf(modifier, "Invalid Java class modifier: " + modifier, NONE, ABSTRACT, FINAL);
        this.packageName = packageName;
        this.accessModifier = accessModifier;
        this.modifier = modifier;
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<Import> getImports() {
        return imports;
    }

    public <J extends JavaClass> J addImport(Import imp) {
        failIfNull(imp, "Empty Java class import");
        imports.add(imp);
        return (J) this;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public <J extends JavaClass> J addAnnotation(Annotation annotation) {
        failIfNull(annotation, "Empty Java class annotation");
        annotations.add(annotation);
        return (J) this;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public String getClassName() {
        return className;
    }

    public String getSuperClass() {
        return superClass;
    }

    public <J extends JavaClass> J setSuperClass(String superClass) {
        failIfBlank(superClass, "Empty super class name");
        this.superClass = superClass;
        return (J) this;
    }

    public List<String> getImplementedInterfaces() {
        return implementedInterfaces;
    }

    public <J extends JavaClass> J addImplementedInterface(String implementedInterface) {
        failIfBlank(implementedInterface, "Empty implemented interface name");
        implementedInterfaces.add(implementedInterface);
        return (J) this;
    }

    public List<Field> getFields() {
        return fields;
    }

    public <J extends JavaClass> J addField(Field field) {
        failIfNull(field, "Empty Java class field");
        fields.add(field);
        return (J) this;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public <J extends JavaClass> J addMethod(Method method) {
        failIfNull(method, "Empty Java class method");
        failIfInstanceOf(method, "Invalid Java class method type: " + method.getClass().getSimpleName(), InterfaceMethod.class, EnumConstructor.class);
        methods.add(method);
        return (J) this;
    }

    @Override
    protected void fillTemplate(ST template) {
        template
                .add("package", packageName)
                .add("name", className)
                .add("imports", imports)
                .add("annotations", annotations)
                .add("accessModifier", accessModifier)
                .add("modifier", modifier)
                .add("superClass", superClass)
                .add("implementedInterfaces", implementedInterfaces)
                .add("fields", fields)
                .add("methods", methods);
    }
}
