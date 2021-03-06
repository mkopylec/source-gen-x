package pl.allegro.tech.sourcegenx.core.java;

import org.stringtemplate.v4.ST;
import pl.allegro.tech.sourcegenx.core.SourceFile;
import pl.allegro.tech.sourcegenx.exceptions.IllegalOperationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.allegro.tech.sourcegenx.core.SourceFileType.JAVA_ANNOTATION;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PACKAGE_PRIVATE;
import static pl.allegro.tech.sourcegenx.core.java.AccessModifier.PUBLIC;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNotOneOf;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

@SuppressWarnings("unchecked")
public class JavaAnnotation extends SourceFile {

    private final String packageName;
    private final List<Import> imports = new ArrayList<>();
    private final List<Annotation> annotations = new ArrayList<>();
    private final AccessModifier accessModifier;
    private final String annotationName;
    private final List<AnnotationElement> elements = new ArrayList<>();

    public JavaAnnotation(String packageName, String annotationName) {
        this(packageName, PUBLIC, annotationName);
    }

    public JavaAnnotation(String packageName, AccessModifier accessModifier, String annotationName) {
        super(JAVA_ANNOTATION);
        failIfBlank(packageName, "Empty Java annotation package name");
        failIfNull(accessModifier, "Empty Java annotation access modifier");
        failIfNotOneOf(accessModifier, "Invalid Java annotation access modifier: " + accessModifier, PUBLIC, PACKAGE_PRIVATE);
        failIfBlank(annotationName, "Empty Java annotation name");
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

    public <J extends JavaAnnotation> J addImport(Import imp) {
        failIfNull(imp, "Empty Java annotation import");
        imports.add(imp);
        return (J) this;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public <J extends JavaAnnotation> J addAnnotation(Annotation annotation) {
        failIfNull(annotation, "Empty Java annotation annotation");
        annotations.add(annotation);
        return (J) this;
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

    public <J extends JavaAnnotation> J addElement(AnnotationElement element) {
        failIfNull(element, "Empty Java annotation element");
        elements.add(element);
        return (J) this;
    }

    public void createSourceFile(String directory) throws IOException {
        super.createSourceFile(directory, annotationName);
    }

    @Override
    @Deprecated
    public void createSourceFile(String directory, String fileName) {
        throw new IllegalOperationException("Cannot specify custom Java annotation source file name");
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
