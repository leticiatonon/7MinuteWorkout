package com.tonon.a7minuteworkout

class Constants {
    companion object{
        fun defaultExerciseList(): ArrayList<ExerciseModel>{
            val exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(1, "Polichinelo", R.drawable.jumping_jacks, false, false)
            exerciseList.add(jumpingJacks)

            val abdominal = ExerciseModel(2, "Abdominal", R.drawable.abdominal, false, false)
            exerciseList.add(abdominal)

            val highKnees = ExerciseModel(3, "Joelho alto", R.drawable.high_knees, false, false)
            exerciseList.add(highKnees)

            val lunge = ExerciseModel(4, "Afundo", R.drawable.lunge, false, false)
            exerciseList.add(lunge)

            val plank = ExerciseModel(5, "Prancha", R.drawable.plank, false, false)
            exerciseList.add(plank)

            val pushUp = ExerciseModel(6,"Flexão", R.drawable.push_up, false, false)
            exerciseList.add(pushUp)

            val shoulderTap = ExerciseModel(7, "Shoulder Tap", R.drawable.shoulder_tap, false, false)
            exerciseList.add(shoulderTap)

            val sidePlank = ExerciseModel(8, "Prancha Lateral", R.drawable.side_plank, false, false)
            exerciseList.add(sidePlank)

            val squat = ExerciseModel(9, "Agachamento", R.drawable.squat, false, false)
            exerciseList.add(squat)

            val stepUp = ExerciseModel(10, "Step Up", R.drawable.step_up, false, false)
            exerciseList.add(stepUp)

            val triceps = ExerciseModel(11, "Tríceps na cadeira", R.drawable.triceps_dip_on_chair, false, false)
            exerciseList.add(triceps)

            val wallSit = ExerciseModel(12, "Cadeira", R.drawable.wall_sit, false, false)
            exerciseList.add(wallSit)


            return exerciseList
        }
    }
}