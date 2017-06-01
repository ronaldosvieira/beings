package frames;

import org.jetbrains.annotations.NotNull;

public class FrameRef {
    private String ref;

    public FrameRef(@NotNull Frame frame) {
        try {
            this.ref = frame.name();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(
                    "Frame ref constructor must receive a non-null frame");
        }
    }

    public Frame retrieve() {
        return KnowledgeBase.retrieveFrame(this.ref);
    }
}
