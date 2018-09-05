# The content below comes from the resource file run.part.sh
# $JAR has been assigne the name of the executable jar file.

#########################################################
#
#  This files shows how to run the generated scrapper
#
#       To run this file type :  bash <run.sh
#
#    ( Sure, you can also make it executable with chmod
#      but you'll have to do that again and again ...  )
#
#########################################################

# First, let's check to see if the executable jar was already compiled.
# If not found, we will try to compile instead.

LOG=Log-$(date).txt

echo "Log for the run.sh sript on : " > "$LOG"
date >> "$LOG"

if [ ! -f target/$JAR ]; then   
    echo "Compilation is needed."
    echo "Preparing to compile target/$JAR"
    mvn -version >> "$LOG"
    if [ $? -ne 0 ] ; then 
        echo "Cannot compile because could not find maven !"
        echo "Go and install the latest maven version and try again ..."
	cat "$LOG"
        exit 1
    fi
    mvn clean package -e
    if [ $? -ne 0 ] ; then
        echo "Could not compile, somiting is wrong."
        echo "Go check the messages above and try again ..."
	cat "$LOG"
        exit 2
	else 
	echo "Things look ok."
	echo "Attempting to run the scrapper ..."
	echo "Next time you run this cripts, the scrapper will run directly with no other message. You can capture its std output."
	echo "Remember, the latest error log is $LOG"
	echo ""

    fi
fi

# Run the scrapper, redirecting debugging/errors to the $LOG file.
java -jar target/$JAR 2>> "$LOG"