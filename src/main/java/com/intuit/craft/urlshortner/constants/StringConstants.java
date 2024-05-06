package com.intuit.craft.urlshortner.constants;

public interface StringConstants {
    interface Error{
        String INVALID_EMAIL = "Empty/Invalid email passed in the request";
        String DATABASE_FAILURE_MESSAGE = "Unable to process database request";

        String URL_NOT_FOUND = "Provided url does not exist in the system";

        String URL_CREATION_ERROR = "Error while creating short url";

        String USER_ID_NOT_EXIST = "User id provided does not exist";

        String USER_CREATION_ERROR = "Unable to create user in the system";
    }
}
