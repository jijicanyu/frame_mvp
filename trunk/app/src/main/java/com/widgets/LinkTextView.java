package com.widgets;

/**
 * Created by aa on 2014/9/18.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.text.*;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 *带有下划线的TextView
 */
public class LinkTextView extends TextView {
    Context mContext;
    boolean dontConsumeNonUrlClicks = true;
    boolean linkHit;
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
    }
    public LinkTextView(Context context) {
        super(context);
        mContext = context;
    }
    public LinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }
    public LinkTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        linkHit = false;
        boolean res = super.onTouchEvent(event);
        if (dontConsumeNonUrlClicks)
            return linkHit;
        return res;
    }
    public void setTextViewHTML(String html) {
        CharSequence sequence = Html.fromHtml(html);
        setText(sequence);
        CharSequence text = getText();
        int end = text.length();
        // Spannable sp = (Spannable) getText();
        SpannableString sp = new SpannableString(getText());
        URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        for (URLSpan url : urls) {
            LinkURLSpan myURLSpan = new LinkURLSpan(url.getURL());
            style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(style);
    }
    public static class LocalLinkMovementMethod extends LinkMovementMethod {
        static LocalLinkMovementMethod sInstance;
        public static LocalLinkMovementMethod getInstance() {
            if (sInstance == null)
                sInstance = new LocalLinkMovementMethod();
            return sInstance;
        }
        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer,
                                    MotionEvent event) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_UP
                    || action == MotionEvent.ACTION_DOWN) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();
                x += widget.getScrollX();
                y += widget.getScrollY();
                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);
                URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
                SpannableStringBuilder style = new SpannableStringBuilder(
                        buffer);
                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        // link[0].onClick(widget);
                        LinkURLSpan linkURLSpan = new LinkURLSpan(
                                link[0].getURL());
                        style.setSpan(linkURLSpan,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        // widget.setText(style);
                        linkURLSpan.onClick(widget);
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        Selection.setSelection(buffer,
                                buffer.getSpanStart(link[0]),
                                buffer.getSpanEnd(link[0]));
                    }
                    if (widget instanceof LinkTextView) {
                        ((LinkTextView) widget).linkHit = true;
                    }
                    return true;
                } else {
                    Selection.removeSelection(buffer);
                    Touch.onTouchEvent(widget, buffer, event);
                    return false;
                }
            }
            return Touch.onTouchEvent(widget, buffer, event);
        }
    }
}

class LinkURLSpan extends ClickableSpan {
    private String mUrl;
    LinkURLSpan(String url) {
        mUrl = url;
    }
    // 去掉下划线
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }
    // 点击超链接时调用
    @Override
    public void onClick(View widget) {
        // TODO Auto-generated method stub
        System.out.println("?" + mUrl);
    }
}


        //使用==================
        /*
        LinkTextView tv = (LinkTextView) convertView
        .findViewById(R.id.tv);
        tv.setTextViewHTML(getResources().getString(R.string.html));
        tv.setMovementMethod(LinkTextView.LocalLinkMovementMethod
        .getInstance());
        //tv.setFocusable(false);//如果在ListView中使用需要让他失去焦点
                */