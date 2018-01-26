package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.clinic.romeo.dental.R;



public class InfoCardView extends FrameLayout implements View.OnClickListener {
    private TextView tvTitle;
    private TextView tvContent;

    private InfoClickListener infoClickListener;

    private String title;
    private String content;

    public InfoCardView(Context context) {
        super(context);
        setup(null);
    }

    public InfoCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(attrs);
    }

    public InfoCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InfoCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup(attrs);
    }

    private void setup(AttributeSet attrs) {
        inflate(getContext(), R.layout.widget_info_card, this);
        bindView();
        setupStyleable(attrs);
        setupView();
    }

    private void bindView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    private void setupStyleable(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.InfoCardView);
            title = typedArray.getString(R.styleable.InfoCardView_icv_title);
            content = typedArray.getString(R.styleable.InfoCardView_icv_content);
            typedArray.recycle();
        }
    }

    private void setupView() {
        tvTitle.setOnClickListener(this);
        tvContent.setOnClickListener(this);
        setTitle(title);
        setContent(content);
    }

    public void setTitle(String title) {
        this.title = title;
        tvTitle.setText(title);
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String content) {
        this.content = content;
        tvContent.setText(content);
    }

    public String getContent() {
        return this.content;
    }

    public void setInfoClickListener(InfoClickListener listener) {
        this.infoClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view == tvTitle) {
            onTitleClick();
        } else if (view == tvContent) {
            onContentClick();
        }
    }

    private void onTitleClick() {
        if (infoClickListener != null) {
            infoClickListener.onTitleClick();
        }
    }

    private void onContentClick() {
        if (infoClickListener != null) {
            infoClickListener.onContentClick();
        }
    }

    public interface InfoClickListener {
        void onTitleClick();

        void onContentClick();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.title = this.title;
        ss.content = this.content;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.title = ss.title;
        this.content = ss.content;
        setTitle(title);
        setContent(content);
    }

    private static class SavedState extends BaseSavedState {
        String title;
        String content;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.title = in.readString();
            this.content = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.title);
            out.writeString(this.content);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}