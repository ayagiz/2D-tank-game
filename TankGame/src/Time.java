
public class Time {
double timeReference;
double currentTime;
public void Time() {
	timeReference=0;
}
public void StartTime() {
	timeReference= System.currentTimeMillis()/1000;
	currentTime=timeReference;
}
public double getTime() {
	currentTime= System.currentTimeMillis()/1000 - timeReference;
	return currentTime;
	
}
}
