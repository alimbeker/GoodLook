package com.example.goodlook.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class FadeInItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        if (holder != null) {
            holder.itemView.alpha = 0f
            val fadeInAnimator = ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f)
            fadeInAnimator.duration = 300 // Adjust the duration of the animation as needed
            fadeInAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    dispatchAddFinished(holder)
                }

                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })
            fadeInAnimator.start()
            return false // Returning false means that RecyclerView will not handle the animation, we handle it manually.
        }
        return super.animateAdd(holder)
    }

    // Override other methods if needed
}
