package alpha.crud;

import alpha.route.IRoute;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public abstract class CRUDRouting<T> implements IRoute {

    // Attributes
    protected final String basePath;
    protected final CRUDController<T> controller;

    // _________________________________________________________________________________________________________________

    public CRUDRouting(String basePath, CRUDController<T> controller) {
        this.basePath = basePath;
        this.controller = controller;
    }

    // _________________________________________________________________________________________________________________

    @Override
    public EndpointGroup routes() {
        return () -> {

            path(basePath, () -> {

                // -------------------------------------------------------------------

                get("/all", this.controller::getAll);

                // -------------------------------------------------------------------

                get("/{id}", this.controller::getById);

                // -------------------------------------------------------------------

                post("/", this.controller::create);

                // -------------------------------------------------------------------

                put("/{id}", this.controller::update);

                // -------------------------------------------------------------------

                delete("/all/safe", this.controller::deleteAllSafe);

                // -------------------------------------------------------------------

                delete("/all", this.controller::deleteAll);

                // -------------------------------------------------------------------

                delete("/{id}", this.controller::deleteById);

            });

        };

    }

}
