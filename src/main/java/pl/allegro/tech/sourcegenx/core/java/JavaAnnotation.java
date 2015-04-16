package pl.allegro.tech.sourcegenx.core.java;

import org.stringtemplate.v4.ST;
import pl.allegro.tech.sourcegenx.core.SourceFile;
import pl.allegro.tech.sourcegenx.core.SourceFileType;
import pl.allegro.tech.sourcegenx.utils.Validator;

import java.util.ArrayList;
import java.util.List;

public class JavaAnnotation extends SourceFile {

    private final String packageName;
    private final List<Import> imports = new ArrayList<>();
    private final List<Annotation> annotations = new ArrayList<>();
    private final AccessModifier accessModifier;
    private final String annotationName;
    private final List<AnnotationElement> elements = new ArrayList<>();

    public JavaAnnotation(String packageName, String annotationName, String directory) {
        this(packageName, annotationName, directory, AccessModifier.PUBLIC);
    }

    public JavaAnnotation(String packageName, String annotationName, String directory, AccessModifier accessModifier) {
        super(annotationName, directory, SourceFileType.JAVA_ANNOTATION);
        Validator.failIfBlank(packageName, "Empty Java annotation package name");
        Validator.failIfBlank(annotationName, "Empty Java annotation name");
        Validator.failIfNull(accessModifier, "Empty Java annotation access modifier");
        Validator.failIfNotOneOf(accessModifier, "Invalid Java annotation access modifier: " + accessModifier, AccessModifier.PUBLIC, AccessModifier.PACKAGE_PRIVATE);
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
        Validator.failIfNull(imp, "Empty Java annotation import");
        imports.add(imp);
        return this;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public JavaAnnotation addAnnotation(Annotation annotation) {
        Validator.failIfNull(annotation, "Empty Java annotation annotation");
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
        Validator.failIfNull(element, "Empty Java annotation element");
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
