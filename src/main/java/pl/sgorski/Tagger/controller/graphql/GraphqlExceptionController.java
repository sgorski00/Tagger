package pl.sgorski.Tagger.controller.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GraphqlExceptionController extends DataFetcherExceptionResolverAdapter {

    private final MessageSource messageSource;

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        String message = messageSource.getMessage("error.generic.message", null, env.getLocale());
        return GraphqlErrorBuilder.newError(env)
                .message(message + ": " + ex.getMessage())
                .errorType(ErrorType.INTERNAL_ERROR)
                .locations(null)
                .build();
    }
}
