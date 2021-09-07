module schach {
    requires javafx.controls;
    requires transitive javafx.graphics;
    
    
    exports schach;
    opens schach;
    exports view;
    opens view;
    exports model;
    opens model;
    exports controller;
    opens controller;
}
