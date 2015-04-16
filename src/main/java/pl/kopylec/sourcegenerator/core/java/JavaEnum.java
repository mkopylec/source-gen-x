package pl.kopylec.sourcegenerator.core.java;

import org.stringtemplate.v4.ST;
import pl.kopylec.sourcegenerator.core.SourceFile;

import java.util.ArrayList;
import java.util.List;

import static pl.kopylec.sourcegenerator.core.SourceFileType.JAVA_ENUM;
import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PUBLIC;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfBlank;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfInstanceOf;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfNotOneOf;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfNull;

public class JavaEnum extends SourceFile {

    private final String packageName;
    private final List<Import> imports = new ArrayList<>();
    private final List<Annotation> annotations = new ArrayList<>();
    private final AccessModifier accessModifier;
    private final String enumName;
    private final List<String> implementedInterfaces = new ArrayList<>();
    private final List<EnumValue> values = new ArrayList<>();
    private final List<Field> fields = new ArrayList<>();
    private final List<Method> methods = new ArrayList<>();

    public JavaEnum(String packageName, String enumName, String directory) {
        this(enumName, packageName, enumName, directory, PUBLIC);
    }

    public JavaEnum(String packageName, String enumName, String directory, AccessModifier accessModifier) {
        this(enumName, packageName, enumName, directory, accessModifier);
    }

    public JavaEnum(String fileName, String packageName, String enumName, String directory) {
        this(fileName, packageName, enumName, directory, PUBLIC);
    }

    public JavaEnum(String fileName, String packageName, String enumName, String directory, AccessModifier accessModifier) {
        super(fileName, directory, JAVA_ENUM);
        failIfBlank(packageName, "Empty Java enum package name");
        failIfBlank(enumName, "Empty Java enum name");
        failIfNull(accessModifier, "Empty Java enum access modifier");
        failIfNotOneOf(accessModifier, "Invalid Java enum access modifier: " + accessModifier, PUBLIC, PACKAGE_PRIVATE);
        this.packageName = packageName;
        this.accessModifier = accessModifier;
        this.enumName = enumName;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<Import> getImports() {
        return imports;
    }

    public JavaEnum addImport(Import imp) {
        failIfNull(imp, "Empty Java enum import");
        imports.add(imp);
        return this;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public JavaEnum addAnnotation(Annotation annotation) {
        failIfNull(annotation, "Empty Java enum annotation");
        annotations.add(annotation);
        return this;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public String getEnumName() {
        return enumName;
    }

    public List<String> getImplementedInterfaces() {
        return implementedInterfaces;
    }

    public JavaEnum addImplementedInterface(String implementedInterface) {
        failIfBlank(implementedInterface, "Empty implemented interface name");
        implementedInterfaces.add(implementedInterface);
        return this;
    }

    public List<EnumValue> getValues() {
        return values;
    }

    public JavaEnum addValue(EnumValue value) {
        failIfNull(value, "Empty enum value");
        values.add(value);
        return this;
    }

    public List<Field> getFields() {
        return fields;
    }

    public JavaEnum addField(Field field) {
        failIfNull(field, "Empty Java enum field");
        fields.add(field);
        return this;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public JavaEnum addMethod(Method method) {
        failIfNull(method, "Empty Java enum method");
        failIfInstanceOf(method, "Invalid Java enum method type: " + method.getClass().getSimpleName(), InterfaceMethod.class, AbstractMethod.class, Constructor.class);
        methods.add(method);
        return this;
    }

    @Override
    protected void fillTemplate(ST template) {
        template
                .add("package", packageName)
                .add("name", enumName)
                .add("imports", imports)
                .add("annotations", annotations)
                .add("accessModifier", accessModifier)
                .add("implementedInterfaces", implementedInterfaces)
                .add("values", values)
                .add("fields", fields)
                .add("methods", methods);
    }
}
