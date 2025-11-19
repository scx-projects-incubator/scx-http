package cool.scx.http.test;

import cool.scx.common.util.$;
import cool.scx.http.media.event_stream.ClientEventStream;
import cool.scx.http.media.event_stream.ServerEventStream;
import cool.scx.http.media.event_stream.SseEvent;
import cool.scx.http.media.event_stream.event.EventClientEventStream;
import cool.scx.io.ScxIO;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;

public class SSETest {

    public static void main() throws IOException {
        test1();
        test2();
    }

    @Test
    public static void test1() throws IOException {
        var outPutStream = new PipedOutputStream();
        var inputStream = new PipedInputStream(outPutStream);

        var byteInput = ScxIO.createByteInput(inputStream);
        var byteOutput = ScxIO.createByteOutput(outPutStream);

        Thread.ofVirtual().start(() -> {
            var s = new ServerEventStream(byteOutput);
            for (int i = 0; i < 100; i++) {
                s.send(SseEvent.of().event("event" + i).id(i + "").comment(i + """
                    多行注释
                    注释第二行
                    表情符号🌴😏😏😏
                    """).data("""
                    多行data\r
                    data第二行\r
                    表情符号🌴😏😏😏\r
                    \r\r\r

                    data 终结符
                    """ + i));
                $.sleep(10);
            }
            s.send(SseEvent.of().event("end"));
            s.close();
        });

        var clientEventStream = new ClientEventStream(byteInput, StandardCharsets.UTF_8);

        while (true) {

            System.out.println("******************** 收到新消息 ********************");
            var sseEvent = clientEventStream.readEvent();
            System.out.println("event: " + sseEvent.event());
            System.out.println("data: " + sseEvent.data());
            System.out.println("id: " + sseEvent.id());
            System.out.println("retry: " + sseEvent.retry());
            System.out.println("comment: " + sseEvent.comment());
            if ("end".equals(sseEvent.event())) {
                clientEventStream.close();
                break;
            }
        }

    }

    @Test
    public static void test2() throws IOException {
        var outPutStream = new PipedOutputStream();
        var inputStream = new PipedInputStream(outPutStream);

        var byteInput = ScxIO.createByteInput(inputStream);
        var byteOutput = ScxIO.createByteOutput(outPutStream);

        Thread.ofVirtual().start(() -> {
            var s = new ServerEventStream(byteOutput);
            for (int i = 0; i < 100; i++) {
                s.send(SseEvent.of().event("event" + i).id(i + "").comment(i + """
                    多行注释
                    注释第二行
                    表情符号🌴😏😏😏
                    """).data("""
                    多行data\r
                    data第二行\r
                    表情符号🌴😏😏😏\r
                    \r\r\r

                    data 终结符
                    """ + i));
                $.sleep(10);
            }
            s.send(SseEvent.of().event("end"));
            s.close();
        });

        var eventStream = EventClientEventStream.of(new ClientEventStream(byteInput, StandardCharsets.UTF_8));

        eventStream.onEvent(sseEvent -> {
            System.out.println("******************** 收到新消息 ********************");
            System.out.println("event: " + sseEvent.event());
            System.out.println("data: " + sseEvent.data());
            System.out.println("id: " + sseEvent.id());
            System.out.println("retry: " + sseEvent.retry());
            System.out.println("comment: " + sseEvent.comment());
            if ("end".equals(sseEvent.event())) {
                eventStream.close();
            }
        });

        eventStream.start();

    }

}
