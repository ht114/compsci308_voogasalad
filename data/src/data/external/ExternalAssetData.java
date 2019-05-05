package data.external;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;

public interface ExternalAssetData {
    /**
     * Saves an image to the database
     *
     * @param imageName   the name of the image to save
     * @param imageToSave the image file that should be saved
     */
    void saveImage(String imageName, File imageToSave);

    /**
     * Saves a sound to the database
     *
     * @param soundName   name of the sound to be saved
     * @param soundToSave sound file to be saved
     */
    void saveSound(String soundName, File soundToSave);

    /**
     * Loads a sound from the database
     *
     * @param soundName name of the sound to be loaded
     * @return an input stream of sound data to be converted to a media object
     */
    InputStream loadSound(String soundName);

    /**
     * Loads an image from the database
     *
     * @param imageName name of the image to be loaded
     * @return an input stream of image data to be converted to an image object
     */
    InputStream loadImage(String imageName);

    /**
     * Removes an image from the database
     *
     * @param imageName name of the image to remove
     * @return true if the image was successfully removed
     * @throws SQLException if operation fails
     */
    boolean removeImage(String imageName) throws SQLException;

    /**
     * Removes a sound from the database
     *
     * @param soundName name of the sound to remove
     * @return true if the sound was successfully removed
     * @throws SQLException if operation fails
     */
    boolean removeSound(String soundName) throws SQLException;

    /**
     * Loads all the images involved in a game specified by prefix
     *
     * @param prefix the gameName + the authorName
     * @return a map of the image names to the input stream data
     */
    Map<String, InputStream> loadAllImages(String prefix) throws SQLException;

    /**
     * Loads all the images involved in a game specified by prefix
     *
     * @param prefix the gameName + the authorName
     * @return a map of the sound names to the input stream data
     */
    Map<String, InputStream> loadAllSounds(String prefix) throws SQLException;
}
