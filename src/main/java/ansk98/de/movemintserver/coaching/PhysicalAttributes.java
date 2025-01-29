package ansk98.de.movemintserver.coaching;

/**
 * Encapsulates physical attributes of a client to generate exercises.
 *
 * @param birthday birthday
 * @param height   height
 * @param weight   weight
 * @param gender   gender
 *
 * @author Anton Skripin (anton.tech98@gmail.com)
 */
public record PhysicalAttributes(String birthday,
                                 Integer height,
                                 Integer weight,
                                 String gender) {
}
