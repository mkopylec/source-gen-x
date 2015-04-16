package pl.kopylec.sourcegenerator.core.java;

import org.stringtemplate.v4.ST;
import pl.kopylec.sourcegenerator.core.SourceFile;

import java.util.ArrayList;
import java.util.List;

import static pl.kopylec.sourcegenerator.core.SourceFileType.JAVA_ANNOTATION;
import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.kopylec.sourcegenerator.core.java.AccessModifier.PUBLIC;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfBlank;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfNotOneOf;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfNull;

public class JavaAnnotation extends SourceFile {

    private final String packageName;
    private final List<Import> imports = new ArrayList<>();
    private final List<Annotation> annotations = new ArrayList<>();
    private final AccessModifier accessModifier;
    private final String annotationName;
    private final List<AnnotationElement> elements = new ArrayList<>();

    public JavaAnnotation(String packageName, String annotationName, String directory) {
        this(packageName, annotationName, directory, PUBLIC);
    }

    public JavaAnnotation(String packageName, String annotationName, String directory, AccessModifier accessModifier) {
        super(annotationName, directory, JAVA_ANNOTATION);
        failIfBlank(packageName, "Empty Java annotation package name");
        failIfBlank(annotationName, "Empty Java annotation name");
        failIfNull(accessModifier, "Empty Java annotation access modifier");
        failIfNotOneOf(accessModifier, "Invalid Java annotation access modifier: " + accessModifier, PUBLIC, PACKAGE_PRIVATE);
        this.packageName = packageName;
        this.accessModifier = accessModifier;
        this.annotationName = annotationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<Import> getImports() {
        return imports;
    }

    public JavaAnnotation addImport(Import imp) {
        failIfNull(imp, "Empty Java annotation import");
        imports.add(imp);
        return this;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public JavaAnnotation addAnnotation(Annotation annotation) {
        failIfNull(annotation, "Empty Java annotation annotation");
        annotations.add(annotation);
        return this;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public List<AnnotationElement> getElements() {
        return elements;
    }

    public JavaAnnotation addElement(AnnotationElement element) {
        failIfNull(element, "Empty Java annotation element");
        elements.add(element);
        return this;
    }

    @Override
    protected void fillTemplate(ST template) {
        template
                .add("package", packageName)
                .add("name", annotationName)
                .add("imports", imports)
                .add("annotations", annotations)
                .add("accessModifier", accessModifier)
                .add("elements", elements);
    }
}
