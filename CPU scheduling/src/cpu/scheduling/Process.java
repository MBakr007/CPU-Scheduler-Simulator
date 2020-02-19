
package cpu.scheduling;

import java.util.Vector;

public class Process implements Cloneable{

   public  String process_name;
   public int arrival_time;
   public int burst_time;
   public int priority;
   public int end_time;
   public int AG_factor;
   public int exe_time;
   public int quantum;
   public int start_time;
   public double waiting_time;
   public double turnaround_time;
   public boolean appearance; 
   public Vector<Integer> historyQuantum = new Vector<>();
   
   protected Object clone() throws CloneNotSupportedException{
		return super.clone();
   }
    public Process() 
    {
        process_name = "";
        arrival_time = 0;
        burst_time = 0;
        priority = 0;
        end_time = 0;
        AG_factor = 0;
        exe_time = 0;
        start_time = 0;
        waiting_time = 0;
        turnaround_time = 0;
        appearance = false;
    }
}
