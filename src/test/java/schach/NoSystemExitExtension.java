package schach;

import java.security.Permission;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

/**
 * 
 * Extension to make sure the junit test does not close after a System.exit
 *
 */
class NoSystemExitExtension implements AfterEachCallback, BeforeEachCallback, TestExecutionExceptionHandler {
    private SecurityManager securityManager;
    @SuppressWarnings("serial")
    
    /**
     * 
     * Static class (exception) to stop the exit of the application
     * 
     */
	static class SystemExitException extends SecurityException{}

    // Preparation
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        securityManager = System.getSecurityManager();
        // Create temporary security manager, throwing an Exception when checking for System.exit permissions
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExit(int status) {
                throw new SystemExitException();
            }
            // No need for it
            @Override
            public void checkPermission(Permission perm){ }
        });
    }
    // Undo the changes to the security manager
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
    	System.out.println("NoSystemExitExtension done");
        // Restore initial security manager
        System.setSecurityManager(securityManager);
    }
    // Handler for the exception
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        // Catch SystemExitException. If any other exception occurred, rethrow it
        if (!(throwable instanceof SystemExitException)) throw throwable;
    }
}
