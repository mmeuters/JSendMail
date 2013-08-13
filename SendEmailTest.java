/*
Copyright (c) 2013 Marlon Meuters

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/*
 * This code is based on work done by Marlon Meuters (CLUSTER ONE GmbH)
 * <marlon.meuters@cluster-one.eu>.
 */

public class SendEmailTest
{
	public static void main(String[] args)
	{
		if (args.length >= 8) {
			// The recipient of the mail
			String recipient = args[0];

			// The sender of the mail
			String sender = args[1];

			// The host-server the mail is sent from (smtp-server host)
			String smtp_host = args[2];

			// The port on which the host-server is listening (smtp-server host port)
			int smtp_host_port = Integer.parseInt(args[3]);

			// The username to authenticate on the smtp server
			String smtp_username = args[4];

			// The fitting password for the above mentioned user
			String smtp_password = args[5];

			// The subject for the mail
			String subject = args[6];

			// The message to be sent
			String message = args[7];

			String attachement = null;
			if (args.length >= 9) {
				attachement = args[8];
			}

			// Simply send the mail using the above parameters
			SendEmail.sendMail(recipient, sender, smtp_host, smtp_host_port, smtp_username, smtp_password, subject, message, attachement);
		} else {
			usage();
		}
	}

	private static void usage()
	{
		System.out.println("This tool was created to test the SendEmail class.");
		System.out.println("Please use the parameters as follows:");
		System.out.println("0: __recipient__");
		System.out.println("1: __sender__");
		System.out.println("2: __smtp_host__");
		System.out.println("3: __smtp_host_port__");
		System.out.println("4: __smtp_username__");
		System.out.println("5: __smtp_password__");
		System.out.println("6: __subject__");
		System.out.println("7: __message__");
		System.out.println("8: __attachement_filename__");
	}
}