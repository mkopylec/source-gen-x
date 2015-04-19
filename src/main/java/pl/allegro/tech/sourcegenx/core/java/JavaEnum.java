package pl.allegro.tech.sourcegenx.core.java;

import org.stringtemplate.v4.ST;
import pl.allegro.tech.sourcegenx.core.SourceFile;
import pl.allegro.tech.sourcegenx.exceptions.IllegalOperationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.allegro.tech.sourcegenx.core.SourceFileType.JAVA_ENUM;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfInstanceOf;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNotOneOf;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

@SuppressWarnings("unchecked")
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

    public JavaEnum(String packageName, String enumName) {
        this(packageName, PUBLIC, enumName);
    }

    public JavaEnum(String packageName, AccessModifier accessModifier, String enumName) {
        super(JAVA_ENUM);
        failIfBlank(packageName, "Empty Java enum package name");
        failIfNull(accessModifier, "Empty Java enum access modifier");
        failIfNotOneOf(accessModifier, "Invalid Java enum access modifier: " + accessModifier, PUBLIC, PACKAGE_PRIVATE);
        failIfBlank(enumName, "Empty Java enum name");
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

    public <J extends JavaEnum> J addImport(Import imp) {
        failIfNull(imp, "Empty Java enum import");
        imports.add(imp);
        return (J) this;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public <J extends JavaEnum> J addAnnotation(Annotation annotation) {
        failIfNull(annotation, "Empty Java enum annotation");
        annotations.add(annotation);
        return (J) this;
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

    public <J extends JavaEnum> J addImplementedInterface(String implementedInterface) {
        failIfBlank(implementedInterface, "Empty implemented interface name");
        implementedInterfaces.add(implementedInterface);
        return (J) this;
    }

    public List<EnumValue> getValues() {
        return values;
    }

    public <J extends JavaEnum> J addValue(EnumValue value) {
        failIfNull(value, "Empty enum value");
        values.add(value);
        return (J) this;
    }

    public List<Field> getFields() {
        return fields;
    }

    public <J extends JavaEnum> J addField(Field field) {
        failIfNull(field, "Empty Java enum field");
        fields.add(field);
        return (J) this;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public <J extends JavaEnum> J addMethod(Method method) {
        failIfNull(method, "Empty Java enum method");
        failIfInstanceOf(method, "Invalid Java enum method type: " + method.getClass().getSimpleName(), InterfaceMethod.class, AbstractMethod.class, Constructor.class);
        if (method instanceof Constructor) {
            failIfNotOneOf(method.getName(), "Invalid Java enum " + enumName + " constructor name: " + method.getName(), enumName);
        }
        methods.add(method);
        return (J) this;
    }

    public void createSourceFile(String directory) throws IOException {
        super.createSourceFile(directory, enumName);
    }

    @Override
    @Deprecated
    public void createSourceFile(String directory, String fileName) {
        throw new IllegalOperationException("Cannot specify custom Java enum source file name");
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
