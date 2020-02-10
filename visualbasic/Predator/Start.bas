Attribute VB_Name = "Start"

Public Sub Main()
    Dim p As New LogParser
    
    Dim log As String, key As Integer, path As String
    
    'path = "c:\temp\"
    'path = "h:\gatech\cs4400\CoCPred\data\"
    'log = "msn.com"
    'key = 12
    
    'GenData.GenData path & log, path & log & ".enc", key
    
    'Dim myMessage As message
    'Set myMessage = p.BeginParsing(path & log & ".enc", key)
    'MsgBox ("From email: " & myMessage.FromPerson.EmailAddress)
    'MsgBox ("date: " & myMessage.DateSent)
    'MsgBox ("recipient 1: " & myMessage.Recipients.Item(1).EmailAddress)
    'MsgBox ("subject: " & myMessage.Subject)
    'MsgBox ("subject words 1: " & myMessage.SubjectWords.Item(2))
    'MsgBox ("message words 1: " & myMessage.MessageWords.Item(1))
    
    'While Not myMessage Is Nothing
    '    Set myMessage = p.GetNextMessage
    'Wend
    
    frmWelcome.Visible = True
End Sub

