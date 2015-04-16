package pl.kopylec.sourcegenerator.core.java;

import org.stringtemplate.v4.ST;
import pl.kopylec.sourcegenerator.core.SourceFile;

import java.util.ArrayList;
import java.util.List;

import static pl.kopylec.sourcegenerator.core.SourceFileType.JAVA_INTERFACE;
import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PUBLIC;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfBlank;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfNotOneOf;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfNull;

public class JavaInterface extends SourceFile {

    private final String packageName;
    private final List<Import> imports = new ArrayList<>();
    private final List<Annotation> annotations = new ArrayList<>();
    private final AccessModifier accessModifier;
    private final String interfaceName;
    private final List<String> superInterfaces = new ArrayList<>();
    private final List<InterfaceConstant> constants = new ArrayList<>();
    private final List<InterfaceMethod> methods = new ArrayList<>();

    public JavaInterface(String packageName, String interfaceName, String directory) {
        this(interfaceName, packageName, interfaceName, directory, PUBLIC);
    }

    public JavaInterface(String packageName, String interfaceName, String directory, AccessModifier accessModifier) {
        this(interfaceName, packageName, interfaceName, directory, accessModifier);
    }

    public JavaInterface(String fileName, String packageName, String interfaceName, String directory) {
        this(fileName, packageName, interfaceName, directory, PUBLIC);
    }

    public JavaInterface(String fileName, String packageName, String interfaceName, String directory, AccessModifier accessModifier) {
        super(fileName, directory, JAVA_INTERFACE);
        failIfBlank(packageName, "Empty Java interface package name");
        failIfBlank(interfaceName, "Empty Java interface name");
        failIfNull(accessModifier, "Empty Java interface access modifier");
        failIfNotOneOf(accessModifier, "Invalid Java interface access modifier: " + accessModifier, PUBLIC, PACKAGE_PRIVATE);
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

    public JavaInterface addImport(Import imp) {
        failIfNull(imp, "Empty Java interface import");
        imports.add(imp);
        return this;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public JavaInterface addAnnotation(Annotation annotation) {
        failIfNull(annotation, "Empty Java interface annotation");
        annotations.add(annotation);
        return this;
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

    public JavaInterface addSuperInterface(String superInterface) {
        failIfBlank(superInterface, "Empty super interface name");
        superInterfaces.add(superInterface);
        return this;
    }

    public List<InterfaceConstant> getConstants() {
        return constants;
    }

    public JavaInterface addConstant(InterfaceConstant constant) {
        failIfNull(constant, "Empty interface constant");
        constants.add(constant);
        return this;
    }

    public List<InterfaceMethod> getMethods() {
        return methods;
    }

    public JavaInterface addMethod(InterfaceMethod method) {
        failIfNull(method, "Empty Java interface method");
        methods.add(method);
        return this;
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
