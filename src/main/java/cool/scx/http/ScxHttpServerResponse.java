package cool.scx.http;

import cool.scx.http.headers.EasyHttpHeadersWriter;
import cool.scx.http.headers.ScxHttpHeaders;
import cool.scx.http.headers.ScxHttpHeadersWritable;
import cool.scx.http.media.MediaWriter;
import cool.scx.http.sender.HttpSendException;
import cool.scx.http.sender.IllegalSenderStateException;
import cool.scx.http.sender.ScxHttpSender;
import cool.scx.http.status_code.ScxHttpStatusCode;

/// ScxHttpServerResponse
///
/// @author scx567888
/// @version 0.0.1
public interface ScxHttpServerResponse extends ScxHttpSender<Void>, EasyHttpHeadersWriter<ScxHttpServerResponse> {

    ScxHttpServerRequest request();

    ScxHttpStatusCode statusCode();

    @Override
    ScxHttpHeadersWritable headers();

    ScxHttpServerResponse statusCode(ScxHttpStatusCode statusCode);

    ScxHttpServerResponse headers(ScxHttpHeaders headers);

    @Override
    Void send(MediaWriter mediaWriter) throws IllegalSenderStateException, HttpSendException;

    //******************** 简化操作 *******************

    default ScxHttpServerResponse statusCode(int statusCode) {
        return statusCode(ScxHttpStatusCode.of(statusCode));
    }

}
