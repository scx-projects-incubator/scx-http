package cool.scx.http;

import cool.scx.http.body.ScxHttpBody;
import cool.scx.http.headers.EasyHttpHeadersReader;
import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.status_code.ScxHttpStatusCode;
import cool.scx.http.version.HttpVersion;

/// ScxHttpClientResponse
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpClientResponse extends EasyHttpHeadersReader {

    ScxHttpStatusCode statusCode();

    HttpVersion version();

    @Override
    ScxHttpHeaders headers();

    ScxHttpBody body();

}
