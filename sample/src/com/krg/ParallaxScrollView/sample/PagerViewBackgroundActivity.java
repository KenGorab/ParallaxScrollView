/*
 * Copyright 2014 Ken Gorab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.krg.ParallaxScrollView.sample;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.krg.ParallaxScrollView.ParallaxScrollView;

/**
 * An example of the implementation of a ParallaxScrollView, as created in Java and having a more
 * interesting background View than a simple image (in this case a ViewPager was used, but it could
 * have been anything).
 */
public class PagerViewBackgroundActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create ParallaxScrollView, which will eventually be the container for everything.
        ParallaxScrollView parallaxScrollView = new ParallaxScrollView(this);
        parallaxScrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // Create the Background View, a ViewPager.
        ViewPager backgroundViewPager = new ViewPager(this);
        backgroundViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
        ImageAdapter adapter = new ImageAdapter(this);
        backgroundViewPager.setAdapter(adapter);

        // Create the Contents View.
        LinearLayout contentView = new LinearLayout(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentView.setBackgroundColor(0xffffffff);
        int padding = getResources().getDimensionPixelSize(R.dimen.content_padding);
        contentView.setPadding(padding, padding, padding, padding);
        TextView textView = new TextView(this);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(R.string.starry_night);
        float textSize = getResources().getDimension(R.dimen.content_text_size);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        contentView.addView(textView);

        // Make the ParallaxScrollView aware of the Background, Content, and Header Views, and set the content of
        // this view to the ParallaxScrollView.
        parallaxScrollView.setBackgroundView(backgroundViewPager);
        parallaxScrollView.setContentView(contentView);
        setContentView(parallaxScrollView);
    }

    /**
     * PagerAdapter which supplies images of Starry Night to the ViewPager.
     */
    public class ImageAdapter extends PagerAdapter {
        private Context mContext;
        private int[] galleryImages = new int[] { R.drawable.starry1, R.drawable.starry2, R.drawable.starry3, R.drawable.starry4 };

        ImageAdapter(Context context){
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return galleryImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public ImageView instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(galleryImages[position]);
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }
}