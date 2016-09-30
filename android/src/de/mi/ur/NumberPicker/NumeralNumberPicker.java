package de.mi.ur.NumberPicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.NumberPicker;

/**
 * Created by Anna-Marie on 01.09.2016.
 *
 * Enables setting the min, max, and default Values of the Number Picker in the xml, separating code and layout
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class NumeralNumberPicker extends NumberPicker {

    public NumeralNumberPicker(Context context) {
        super(context);
    }

    public NumeralNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttributeSet(attrs);
    }

    public NumeralNumberPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        processAttributeSet(attrs);
    }

    private void processAttributeSet(AttributeSet attrs) {
        //This method reads the parameters given in the xml file and sets the properties according to it
        this.setMinValue(attrs.getAttributeIntValue(null, "min", 0));
        this.setMaxValue(attrs.getAttributeIntValue(null, "max", 0));
        this.setValue(attrs.getAttributeIntValue(null, "default", 0));
    }


}
