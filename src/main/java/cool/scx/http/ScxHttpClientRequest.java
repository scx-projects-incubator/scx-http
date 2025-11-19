package cool.scx.http;

import cool.scx.http.headers.EasyHttpHeadersWriter;
import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.http.method.ScxHttpMethod;
import cool.scx.http.sender.HttpSendException;
import cool.scx.http.sender.IllegalSenderStateException;
import cool.scx.http.sender.ScxHttpSender;
import cool.scx.http.uri.ScxURI;
import cool.scx.http.uri.ScxURIWritable;

/// ScxHttpClientRequest
///
/// 和 ScxHttpClientResponse, ScxHttpServerRequest, ScxHttpServerResponse 有所不同.
///
/// ScxHttpClientRequest 并不表示一个已经存在的请求或响应对象, 而是更接近 Builder.
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpClientRequest extends ScxHttpSender<ScxHttpClientResponse>, EasyHttpHeadersWriter<ScxHttpClientRequest> {

    ScxHttpMethod method();

    ScxURIWritable uri();

    @Override
    ScxHttpHeadersWritable headers();

    ScxHttpClientRequest method(ScxHttpMethod method);

    ScxHttpClientRequest uri(ScxURI uri);

    ScxHttpClientRequest headers(ScxHttpHeaders headers);

    @Override
    ScxHttpClientResponse send(MediaWriter mediaWriter) throws IllegalSenderStateException, HttpSendException;

    //******************** 简化操作 *******************

    default ScxHttpClientRequest method(String method) {
        return method(ScxHttpMethod.of(method));
    }

    default ScxHttpClientRequest uri(String uri) {
        return uri(ScxURI.of(uri));
    }

}
