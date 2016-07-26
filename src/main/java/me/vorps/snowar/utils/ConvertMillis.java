package me.vorps.snowar.utils;

/**
 * Project Hub Created by Vorps on 01/02/2016 at 01:41.
 */
public class ConvertMillis {

    /**
     * Time lang fr
     * @param time long
     * @return String
     */
	public static String convertMillisToDate(long time){
        long days;
        long hours;
        long minutes;
        long seconds;
        String messageTemps;
		messageTemps = "";
		if(time >= 86400000L){
			days = time / 86400000L;
			if(days == 1){
				messageTemps = days+" Jour ";
			} else {
				messageTemps = days+" Jours ";
			}
			time-=86400000L*days;
		}
		if(time < 86400000L && time >= 3600000L){
			hours = time / 3600000L;
			if(hours == 1){
				messageTemps+= hours+" Heure ";
			} else {
				messageTemps+= hours+" Heures ";
			}
			time-= 3600000L*hours;
		}
		if(time < 3600000L && time >= 60000L){
			minutes = time / 60000L;
			if(minutes == 1){
				messageTemps+= minutes+" Minute ";
			} else {
				messageTemps+= minutes+" Minutes ";
			}
			time-= 60000L*minutes;
		}
		if(time < 60000L && time >= 1000L){
			seconds = time / 1000L;
			if(seconds == 1){
				messageTemps+= seconds+" seconde ";
			} else {
				messageTemps+= seconds+" secondes ";
			}
		}
		return messageTemps;
	}

    /**
     * Time numeric
     * @param time long
     * @return String
     */
    public static String convertMillisToTime(long time){
        long days;
        long hours;
        long minutes;
        long seconds;
        String dayTmp = "00-";
        String hourTmp = "00:";
        String minuteTmp = "00:";
        String secondsTmp = "00";
        if(time >= 86400000L){
            days = time / 86400000L;
            if(days < 10){
                dayTmp = "0"+days+"-";
            } else {
                dayTmp = days+"-";
            }
            time-=86400000L*days;
        }
        if(time < 86400000L && time >= 3600000L){
            hours = time / 3600000L;
            if(hours < 10){
                hourTmp = "0"+hours+":";
            } else {
                hourTmp = hours+":";
            }
            time-= 3600000L*hours;
        }
        if(time < 3600000L && time >= 60000L){
            minutes = time / 60000L;
            if(minutes < 10){
                minuteTmp = "0"+minutes+":";
            } else {
                minuteTmp = minutes+":";
            }
            time-= 60000L*minutes;
        }
        if(time < 60000L && time >= 1000L){
            seconds = time / 1000L;
            if(seconds < 10){
                secondsTmp = "0"+seconds;
            } else {
                secondsTmp = ""+seconds;
            }
        }
        return dayTmp+hourTmp+minuteTmp+secondsTmp;
    }
}
