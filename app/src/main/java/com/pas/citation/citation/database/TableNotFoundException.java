package com.pas.citation.citation.database;

/**
 * Created by Paskal on 18.12.2014.
 */
public class TableNotFoundException extends RuntimeException  {
    /**
     * Constructs a new {@code TableNotFoundException}
     *
     * @param detailMessage
     *            the detail message for this exception.
     */
    public TableNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code TableNotFoundException}
     *
     * @param detailMessage
     *            the detail message for this exception.
     * @param throwable
     *            the cause of this exception.
     */
    public TableNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
