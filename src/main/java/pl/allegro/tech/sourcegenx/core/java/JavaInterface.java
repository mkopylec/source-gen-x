package pl.allegro.tech.sourcegenx.core.java;

import org.stringtemplate.v4.ST;
import pl.allegro.tech.sourcegenx.core.SourceFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.allegro.tech.sourcegenx.core.SourceFileType.JAVA_INTERFACE;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNotOneOf;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

@SuppressWarnings("unchecked")
public class JavaInterface extends SourceFile {

    private final String packageName;
    private final List<Import> imports = new ArrayList<>();
    private final List<Annotation> annotations = new ArrayList<>();
    private final AccessModifier accessModifier;
    private final String interfaceName;
    private final List<String> superInterfaces = new ArrayList<>();
    private final List<InterfaceConstant> constants = new ArrayList<>();
    private final List<InterfaceMethod> methods = new ArrayList<>();

    public JavaInterface(String packageName, String interfaceName) {
        this(packageName, PUBLIC, interfaceName);
    }

    public JavaInterface(String packageName, AccessModifier accessModifier, String interfaceName) {
        super(JAVA_INTERFACE);
        failIfBlank(packageName, "Empty Java interface package name");
        failIfNull(accessModifier, "Empty Java interface access modifier");
        failIfNotOneOf(accessModifier, "Invalid Java interface access modifier: " + accessModifier, PUBLIC, PACKAGE_PRIVATE);
        failIfBlank(interfaceName, "Empty Java interface name");
        this.packageName = packageName;
        this.accessModifier = accessModifier;
        this.interfaceName = interfaceName;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<Import> getImports() {
        return imports;
    }

    public <J extends JavaInterface> J addImport(Import imp) {
        failIfNull(imp, "Empty Java interface import");
        imports.add(imp);
        return (J) this;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public <J extends JavaInterface> J addAnnotation(Annotation annotation) {
        failIfNull(annotation, "Empty Java interface annotation");
        annotations.add(annotation);
        return (J) this;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public List<String> getSuperInterfaces() {
        return superInterfaces;
    }

    public <J extends JavaInterface> J addSuperInterface(String superInterface) {
        failIfBlank(superInterface, "Empty super interface name");
        superInterfaces.add(superInterface);
        return (J) this;
    }

    public List<InterfaceConstant> getConstants() {
        return constants;
    }

    public <J extends JavaInterface> J addConstant(InterfaceConstant constant) {
        failIfNull(constant, "Empty interface constant");
        constants.add(constant);
        return (J) this;
    }

    public List<InterfaceMethod> getMethods() {
        return methods;
    }

    public <J extends JavaInterface> J addMethod(InterfaceMethod method) {
        failIfNull(method, "Empty Java interface method");
        methods.add(method);
        return (J) this;
    }

    public void createSourceFile(String directory) throws IOException {
        super.createSourceFile(directory, interfaceName);
    }

    @Override
    protected void fillTemplate(ST template) {
        template
                .add("package", packageName)
                .add("name", interfaceName)
                .add("imports", imports)
                .add("annotations", annotations)
                .add("accessModifier", accessModifier)
                .add("superInterfaces", superInterfaces)
                .add("constants", constants)
                .add("methods", methods);
    }
}
