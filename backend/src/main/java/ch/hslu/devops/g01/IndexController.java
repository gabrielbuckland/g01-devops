package ch.hslu.devops.g01;

import io.micronaut.context.annotation.Value;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;

@Controller("/")
public class IndexController {

    @Value("${COMMIT_SHA:unknown}")
    @Nullable
    private String commitSha;

    @Get()
    public CommitDto commit() {
        return new CommitDto(commitSha == null ? "unknown" : commitSha);
    }

    public record CommitDto(String sha) { }
}
