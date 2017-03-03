package be.yildiz.launcher.files;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import be.yildiz.common.util.Checker;

/**
 * A file description just contains metadata for a file: its name, its CRC32 and its size.
 * 
 * @author Van den Borre Grégory
 */
@XmlRootElement(name = "file")
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public final class FileDescription {

    /**
     * File name.
     */
    private String name;

    /**
     * File CRC.
     */
    private long crc;

    /**
     * File size.
     */
    private long size;

    /**
     * Create a new File description.
     * 
     * @param name
     *            The file name, cannot be null.
     * @param crc
     *            The file CRC value, must be >= 0.
     * @param size
     *            The file size, must be >=0.
     */
    public FileDescription(@NonNull final String name, final long crc, final long size) {
        super();
        Checker.exceptionNotPositive(size);
        Checker.exceptionNotPositive(crc);
        this.name = name;
        this.crc = crc;
        this.size = size;
    }
}
