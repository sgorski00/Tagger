package pl.sgorski.Tagger.exception;

public class OAuth2MissingAttributeException extends RuntimeException {

    public OAuth2MissingAttributeException(String attributeName) {
        super("Logged user is missing required attribute: " + attributeName);
    }
}
