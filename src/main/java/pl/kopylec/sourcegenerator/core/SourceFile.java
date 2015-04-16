package pl.kopylec.sourcegenerator.core;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ErrorBuffer;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.endsWithAny;
import static pl.kopylec.sourcegenerator.utils.TemplateProvider.getTemplate;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfBlank;
import static pl.kopylec.sourcegenerator.utils.Validator.failIfNull;

public abstract class SourceFile {

    private final String fileName;
    private String directory;
    private final SourceFileType fileType;

    protected SourceFile(String fileName, String directory, SourceFileType fileType) {
        failIfBlank(fileName, "Empty source file name");
        failIfBlank(directory, "Empty source file directory");
        failIfNull(fileType, "Empty source file type");
        this.fileName = fileName;
        this.directory = correctDirectory(directory);
        this.fileType = fileType;
    }

    public void createSourceFile() throws IOException {
        ST template = getTemplate(fileType);
        fillTemplate(template);
        File outputFile = new File(assembleFilePath());
        outputFile.getParentFile().mkdirs();
        template.write(outputFile, new ErrorBuffer());
    }

    @Override
    public String toString() {
        ST template = getTemplate(fileType);
        fillTemplate(template);
        return template.render();
    }

    public String getFileName() {
        return fileName;
    }

    public String getDirectory() {
        return directory;
    }

    public SourceFileType getFileType() {
        return fileType;
    }

    protected abstract void fillTemplate(ST template);

    private String assembleFilePath() {
        return directory + fileName + fileType.getFileExtension();
    }

    private String correctDirectory(String directory) {
        if (!endsWithAny(directory, "/", "\\")) {
            directory += "/";
        }
        return directory;
    }
}
