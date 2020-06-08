package edu.iis.mto.blog.domain.errors;

public class DomainError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String USER_NOT_FOUND = "unknown user";
    public static final String POST_NOT_FOUND = "unknown post";
    public static final String SELF_LIKE = "cannot like own post";
    public static final String USER_NOT_CONFIRMED = "only confirmed user can set likes";
    public static final String USER_REMOVED = "cannot find posts of removed user";

    public DomainError(String msg) {
        super(msg);
    }

}
