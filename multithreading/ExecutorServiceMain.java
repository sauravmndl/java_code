package multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceMain {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    Runnable runnableTask = () -> {
      try {
        TimeUnit.MILLISECONDS.sleep(300);
        System.out.println("I'm in thread runnable");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };
    executorService.execute(runnableTask);

    Callable<String> callableTask = () -> {
      TimeUnit.MILLISECONDS.sleep(1000);
      System.out.println("I'm in thread callable.");
      return "Task's execution";
    };

    Future<String> future = executorService.submit(callableTask);
    String result = future.get();
    System.out.println("Result of submit: " + result);


    List<Callable<String>> callableTasks = new ArrayList<>();
    callableTasks.add(callableTask);
    callableTasks.add(callableTask);
    callableTasks.add(callableTask);
    callableTasks.add(callableTask);
    callableTasks.add(callableTask);
    callableTasks.add(callableTask);
    callableTasks.add(callableTask);

    long startTime = System.currentTimeMillis();
    System.out.println("Start Time: "+ startTime);
    List<Future<String>> futures = executorService.invokeAll(callableTasks);

     for (Future f: futures) {
       System.out.println("Result fo invoke all. Done: --> " + f.isDone() + " Result: --> "+ f.get());
     }
     long endTime = System.currentTimeMillis();
     System.out.println("End Time: "+ endTime);
     System.out.println("Total time taken: "+ ((endTime-startTime)/1000) + " sec");

     // shutdown gracefully
      executorService.shutdown();
      try {
        if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
          executorService.shutdownNow();
        }
      } catch (InterruptedException e) {
        executorService.shutdownNow();
      }

  }


//  public multithreading.ExecutorServiceMain() throws InterruptedException {
//  }
}

//  Runnable runnableTask = () -> {
//    try {
//      TimeUnit.MILLISECONDS.sleep(300);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//  };



