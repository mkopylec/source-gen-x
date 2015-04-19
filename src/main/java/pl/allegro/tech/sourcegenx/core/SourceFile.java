package pl.allegro.tech.sourcegenx.core;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ErrorBuffer;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.endsWithAny;
import static pl.allegro.tech.sourcegenx.utils.TemplateProvider.getTemplate;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfBlank;
import static pl.allegro.tech.sourcegenx.utils.Validator.failIfNull;

public abstract class SourceFile {

    private final SourceFileType fileType;

    protected SourceFile(SourceFileType fileType) {
        failIfNull(fileType, "Empty source file type");
        this.fileType = fileType;
    }

    public void createSourceFile(String directory, String fileName) throws IOException {
        failIfBlank(fileName, "Empty source file name");
        failIfBlank(directory, "Empty source file directory");
        directory = correctDirectory(directory);
        ST template = getTemplate(fileType);
        fillTemplate(template);
        File outputFile = new File(assembleFilePath(directory, fileName));
        outputFile.getParentFile().mkdirs();
        template.write(outputFile, new ErrorBuffer());
    }

    @Override
    public String toString() {
        ST template = getTemplate(fileType);
        fillTemplate(template);
        return template.render();
    }

    public SourceFileType getFileType() {
        return fileType;
    }

    protected abstract void fillTemplate(ST template);

    private String assembleFilePath(String directory, String fileName) {
        return directory + fileName + fileType.getFileExtension();
    }

    private String correctDirectory(String directory) {
        if (!endsWithAny(directory, "/", "\\")) {
            directory += "/";
        }
        return directory;
    }
}
