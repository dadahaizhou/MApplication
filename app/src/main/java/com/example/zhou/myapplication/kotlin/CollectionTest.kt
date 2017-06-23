package com.example.zhou.myapplication

class CollectionTest  {

   /**静态方法**/
    companion object{
        fun getNumberArr():Array<String>{
           var array=Array(8,{i->"132456700$i"})
            return  array
        }
    }
        fun getEmployer():List<Employer>{
            var array= mutableListOf<Employer>()
            for(i in 1..10){
                var emp=Employer("name$i","1322345678$i")
                emp.id=i.toString()
//                emp.name="name$i"
//                emp.number="1322345678$i"
                array.add(emp)
            }
            return  array
        }

}
