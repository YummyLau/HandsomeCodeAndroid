package com.example.code.html;

import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.util.ArrayDeque;

/**
 * Created by yummyLau on 2018/6/22.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class HtmlParser implements Html.TagHandler, ContentHandler {

    /**
     * 自定义实现
     */
    private TextView textView;
    private final HtmlImageGetter imageGetter;
    private final HtmlTagHandler handler;

    private ContentHandler wrapped;
    private Editable text;
    private ArrayDeque<Boolean> tagStatus = new ArrayDeque<>();

    private HtmlParser(TextView textView, HtmlTagHandler handler, HtmlImageGetter imageGetter) {
        this.textView = textView;
        this.handler = handler;
        this.imageGetter = imageGetter;
    }

    public HtmlImageGetter getImageGetter() {
        return imageGetter;
    }

    public TextView getTextView() {
        return textView;
    }

    public interface TagHandler {
        // return true here to indicate that this tag was handled and
        // should not be processed further
        boolean handleTag(HtmlParser parser, boolean opening, String tag, Editable output, Attributes attributes);
    }

    public static String getValue(Attributes attributes, String name) {
        for (int i = 0, n = attributes.getLength(); i < n; i++) {
            if (name.equals(attributes.getLocalName(i)))
                return attributes.getValue(i);
        }
        return null;
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (wrapped == null) {
            // record result object
            text = output;

            // record current content handler
            wrapped = xmlReader.getContentHandler();

            // replace content handler with our own that forwards to calls to original when needed
            xmlReader.setContentHandler(this);

            // handle endElement() callback for <inject/> tag
            tagStatus.addLast(Boolean.FALSE);
        }
    }


    @Override
    public void setDocumentLocator(Locator locator) {
        wrapped.setDocumentLocator(locator);
    }

    @Override
    public void startDocument() throws SAXException {
        wrapped.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        wrapped.endDocument();
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        wrapped.startPrefixMapping(prefix, uri);
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        wrapped.endPrefixMapping(prefix);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        boolean isHandled = handler.handleTag(this, true, localName, text, atts);
        tagStatus.addLast(isHandled);
        if (!isHandled) {
            wrapped.startElement(uri, localName, qName, atts);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!tagStatus.removeLast()) {
            wrapped.endElement(uri, localName, qName);
        }
        handler.handleTag(this, false, localName, text, null);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        wrapped.characters(ch, start, length);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        wrapped.ignorableWhitespace(ch, start, length);
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        wrapped.processingInstruction(target, data);
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        wrapped.skippedEntity(name);
    }


    public static void buildSpannedTextByHtml(TextView textView, String html) {
        if (textView == null || TextUtils.isEmpty(html)) {
            return;
        }
        textView.setText(Html.fromHtml("<inject/>" + html, null, new HtmlParser(textView, new HtmlTagHandler(), new HtmlImageGetter(textView))));
    }
}
