VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
  Persistable = 0  'NotPersistable
  DataBindingBehavior = 0  'vbNone
  DataSourceBehavior  = 0  'vbNone
  MTSTransactionMode  = 0  'NotAnMTSObject
END
Attribute VB_Name = "Message"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = True
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = False
Attribute VB_Ext_KEY = "SavedWithClassBuilder6" ,"Yes"
Attribute VB_Ext_KEY = "Top_Level" ,"Yes"

Public FromPerson As PersonEmail

Public Recipients As New Collection

Public DateSent As String

Public Subject As String

Public SubjectWords As New Collection

Public message As String

Public MessageWords As New Collection


Public Function NewPersonEmail(Email As String, FirstName As String, LastName As String) As PersonEmail
    Dim NPE As New PersonEmail
    
    NPE.EmailAddress = Email
    NPE.FirstName = FirstName
    NPE.LastName = LastName
    
    Set NewPersonEmail = NPE
End Function

Public Sub SetSender(Email As String, FirstName As String, LastName As String)
    Set FromPerson = NewPersonEmail(Email, FirstName, LastName)
End Sub

Public Function NumRecipients() As Integer
    NumRecipients = Recipients.Count
End Function

Public Sub AddRecipient(Email As String, FirstName As String, LastName As String)
    Dim rcp As PersonEmail
    
    Set rcp = NewPersonEmail(Email, FirstName, LastName)
    Recipients.Add rcp
End Sub

Public Sub SetDate(dateStr As String)
    Dim i As Integer
    Dim dt As String
    
    Dim tm As String
    Dim temp As String
       
    i = InStr(1, dateStr, " ", vbTextCompare)
    i = InStr(i + 1, dateStr, " ", vbTextCompare)
    i = InStr(i + 1, dateStr, " ", vbTextCompare)
    
    dt = Format(Left(dateStr, i), "mm-dd-yyyy")
    temp = Right(dateStr, Len(dateStr) - i)
    i = InStr(1, temp, " ", vbTextCompare)
    
    tm = Left(temp, i) & ":" & Right(temp, Len(temp) - i)
    
    DateSent = dt & " " & tm
End Sub
Public Sub SetSubject(Subj As String)
    Subject = Subj
    
    StringToArray Subj, SubjectWords
End Sub

Public Sub SetMessage(text As String)
    message = text
    
    StringToArray text, MessageWords
End Sub

Private Sub StringToArray(text As String, c As Collection)
    Dim token As String
    Dim i As Integer
    
    i = 1
    
    token = sGetToken(text, i)
        
    While Not IsEmpty(token) And Not IsNull(token) And token <> ""
        c.Add (token)
        i = i + 1
        
        token = sGetToken(text, i)
    Wend
End Sub

' From vbcode.com, William M. Rawls
Public Function sGetToken(ByVal sAllTokens As String, Optional ByVal iToken As Integer = 1, Optional ByVal sDelim As String = " ") As String
    Static iCurTokenLocation As Long ' Character position of the first delimiter string
    Static nDelim As Integer            ' Length of the delimiter string
    nDelim = Len(sDelim)

    If iToken < 1 Or nDelim < 1 Then
     ' Negative or zeroth token or empty delimiter strings mean an empty token
       Exit Function
    ElseIf iToken = 1 Then
     ' Quickly extract the first token
       iCurTokenLocation = InStr(sAllTokens, sDelim)
       If iCurTokenLocation > 1 Then
          sGetToken = Left(sAllTokens, iCurTokenLocation - 1)
       ElseIf iCurTokenLocation = 1 Then
          sGetToken = ""
       Else
          sGetToken = sAllTokens
       End If
       Exit Function
    Else
     ' Find the Nth token
       Do
          iCurTokenLocation = InStr(sAllTokens, sDelim)
          If iCurTokenLocation = 0 Then
             Exit Function
          Else
             sAllTokens = Mid(sAllTokens, iCurTokenLocation + nDelim)
          End If
          iToken = iToken - 1
       Loop Until iToken = 1

     ' Extract the Nth token (Which is the next token at this point)
       iCurTokenLocation = InStr(sAllTokens, sDelim)
       If iCurTokenLocation > 0 Then
          sGetToken = Left(sAllTokens, iCurTokenLocation - 1)
          Exit Function
       Else
          sGetToken = sAllTokens
          Exit Function
       End If
    End If
End Function

